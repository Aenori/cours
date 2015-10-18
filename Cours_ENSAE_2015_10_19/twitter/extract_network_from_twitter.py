import tweepy
import logging
logger = logging.getLogger(__name__)
import sqlite3
import datetime as dt
import time
import json
import time
import pprint
import traceback

from utils.quota_management import print_all_used_api_rate_limit_status
from oc_request.twitter_auth import get_twitter_api
from oc_request.db_client import Sqlite3Client
from db_filter_json import filter_user_json

api = get_twitter_api()
sqlite3_client_netword_db = Sqlite3Client(db_name = "twitter_for_network.db" )
sqlite_db_client = sqlite3_client_netword_db

def get_available_query_generator( path ):
  def get_available_query(api_rate_limit_status = None, wait = False):
    if api_rate_limit_status is None:
      api_rate_limit_status = api.rate_limit_status()
    follow_id_rls = api_rate_limit_status['resources'][path[0]][path[1]]
    remaining = follow_id_rls['remaining']
    reset = dt.datetime.fromtimestamp( follow_id_rls['reset'] )
    return ( remaining, reset )
  return get_available_query

get_followers_id_remaining_queries = get_available_query_generator( ('followers', '/followers/ids') )
get_users_lookup_remaining_queries = get_available_query_generator( ('users', '/users/lookup') )
get_available_ressources           = get_available_query_generator( ('application', '/application/rate_limit_status') )

class TwitterScanner:
  def __init__(self, sqlite_db_client):
    self.sqlite_db_client = sqlite_db_client
    self.known_users_id   = set( it for it in sqlite_db_client.iter_on_users_ids(verbose = True) ) 
    self.unknown_users_id = set()
    self.error_users_id = set()
    self.screen_name_list = []

  def get_all_followers_from_screen_name_list(self, screen_name_list):
    self.screen_name_list = screen_name_list
    print("Processing list : {0}".format( self.screen_name_list ) )
    for it in self.screen_name_list:
      try: 
        print("### Processing {0} ###".format( it ) )
        self.get_all_followers( it )
      except Exception as e:
        print("ERROR : processing {0} failed with exception :".format(it) )
        print(e)
        print(traceback.format_exc())
    while self.unknown_users_id:
      api_rate_limit_status = api.rate_limit_status()
      (nb_available_ressources_request, arr_reset) = get_available_ressources(api_rate_limit_status = api_rate_limit_status) 
      (nb_users_look_up_available, u_lu_reset)     = get_users_lookup_remaining_queries(api_rate_limit_status = api_rate_limit_status)
      
      if nb_available_ressources_request < 5:
        self.wait_until_query_available( (arr_reset,), "No available ressources request available" ) 
      
      if nb_users_look_up_available == 0: 
        self.wait_until_query_available( (u_lu_reset,), "No query available")
        continue

      self.search_for_unknown_users( nb_users_look_up_available )


  def get_all_followers(self, screen_name):
    """Regarding cursor and db schema:
       0 => user_id, 1 => cursor, 2 => prev_cursor, 3 => next_cursor, 4 => content
    """
    if self.screen_name_list == []: self.screen_name_list = [screen_name]   
 
    user = self.sqlite_db_client.get_one_user( screen_name )
    if user is None:
      user = api.get_user(screen_name = screen_name)._json
      self.sqlite_db_client.update_one_user(user)
    self.current_scan_user = user 
    self.current_scan_all_followers = self.sqlite_db_client.get_all_followers_id_page( user_id = user['id'] )  

    for it_page in self.current_scan_all_followers:
      self.unknown_users_id.update( set( it_page[-1] ) - self.known_users_id )
    print( "Currently {0} known users and {1} unknown users".format( len( self.known_users_id), len( self.unknown_users_id ) ) )
    self._get_all_followers()  

  def _get_next_cursor(self):
    if self.current_scan_all_followers:
      return self.current_scan_all_followers[-1][3]
    return -1 

  def _add_current_followers_id_page( self, current_cursor, prev_cursor, next_cursor, page ):
    try:
      page_with_cursor = (self.current_scan_user['id'], current_cursor, prev_cursor, next_cursor, page )
      self.sqlite_db_client.add_one_followers_id_page( *page_with_cursor ) 

      self.unknown_users_id.update( set(page) - self.known_users_id - self.error_users_id )

      self.current_scan_all_followers.append( page_with_cursor )
      self.sqlite_db_client.commit()
      return True
    except Exception as e:
      print( "Exception while adding pages : {0}".format( e ) )
      print( "there was no commit en db" )
      return False 

  def _add_users_lookup( self, users_look_up, users_id ):
    users_id = set(users_id)
    for it_user in users_look_up:
      self.sqlite_db_client.update_one_user( filter_user_json( it_user._json ) )
      self.known_users_id.add( it_user.id )
      self.unknown_users_id.remove( it_user.id )
      users_id.remove( it_user.id )
    if len(users_id):
      print( "Found {0} users with errors : {1}".format( len(users_id), users_id ) )
      self.error_users_id.update( users_id )
      for it_id in users_id:
        self.unknown_users_id.remove( it_id ) 
    self.sqlite_db_client.commit()

  @staticmethod
  def wait_until_query_available( reset_list, msg ): 
    second_to_wait = (min(reset_list) - dt.datetime.now() ).total_seconds() + 5
    if second_to_wait > 0:
      print( msg + ", waiting {0}, until {1}".format(second_to_wait, min(reset_list)) )
      time.sleep( second_to_wait )

  def search_for_unknown_users( self, nb_users_look_up_available ):
    print( "Currently {0} known users and {1} unknown users".format( len( self.known_users_id), len( self.unknown_users_id ) ) )
    try:
      for it in range( nb_users_look_up_available ):
        if len( self.unknown_users_id ) == 0:
          print( "Hourra ! No unknown user :-)" )
          break
        users_id = []
        for it, it_id in enumerate( self.unknown_users_id ):
          if it == 100:
            break           
          users_id.append( it_id )
        users_look_up = api.lookup_users(user_ids=users_id, include_entities=True)
        self._add_users_lookup( users_look_up, users_id )
    except Exception as e:
      if "Twitter error response: status code = 404" in str(e):
        print("Error 404 received, i supposed we asked for non existing user.")
        print("Current unknown list ids :")
        print( self.unknown_users_id )
        self.error_users_id.update( self.unknown_users_id )
        self.unknown_users_id = set() 
        print("Going for next in list ...")
        return
      raise e

  def search_for_followers_id(self, nb_followers_id_available):
    for it_page_number in range(nb_followers_id_available):
      current_cursor = self._get_next_cursor()
      if current_cursor == 0:
        print("Encountered 0 for_current_cursor, exiting ...".format( current_cursor ) )
        return False
      (page, (prev_cursor, next_cursor)) = api.followers_ids( 
        user_id = self.current_scan_user['id'], cursor = current_cursor )
      if len(page) == 0:
        print("No page returned with cursor {0}, exiting ...".format( current_cursor ) )
        return False
      assert( self._add_current_followers_id_page( current_cursor, prev_cursor, next_cursor, page ) )        
    return True

  def _get_all_followers(self):
    continue_follow_id_search = True
    nb_ok_fails = 3 
    while (nb_ok_fails 
           and (continue_follow_id_search 
               or ((self.screen_name_list[-1] == self.current_scan_user['screen_name']) 
                    and len(self.unknown_users_id)))):
      try: 
        api_rate_limit_status = api.rate_limit_status()
        (nb_available_ressources_request, arr_reset) = get_available_ressources(api_rate_limit_status = api_rate_limit_status) 
        (nb_followers_id_available , f_id_reset)     = get_followers_id_remaining_queries(api_rate_limit_status = api_rate_limit_status)
        (nb_users_look_up_available, u_lu_reset)     = get_users_lookup_remaining_queries(api_rate_limit_status = api_rate_limit_status)
        
        if nb_available_ressources_request < 5:
          self.wait_until_query_available( (arr_reset,), "No available ressources request available" ) 
        
        if (nb_followers_id_available, nb_users_look_up_available) == (0,0): 
          self.wait_until_query_available( (f_id_reset,u_lu_reset), "No query available")
          continue

        if nb_followers_id_available == 0 and (len(self.unknown_users_id) == 0):
          self.wait_until_query_available( (f_id_reset,), "No users to lookup, and no f_ids query" )
          continue

        continue_follow_id_search = self.search_for_followers_id( nb_followers_id_available )
        self.search_for_unknown_users( nb_users_look_up_available )

      except Exception as e:
        print("Iteration failed with exception :")
        print(e)  
        print("Traceback")
        print(traceback.format_exc())
        
        nb_ok_fails -= 1
        if nb_ok_fails:
          time.sleep(5*60)

with open('configuration/twitter_accounts_all.csv', 'r') as f:
  screen_name_list = [ it.split(";")[2] for it in f ]
  del screen_name_list[0]
  screen_name_list = [ it for it in screen_name_list if it != '?' ]
       
twitter_scanner = TwitterScanner( sqlite3_client_netword_db )
twitter_scanner.get_all_followers_from_screen_name_list( screen_name_list )
#pprint.pprint( api.rate_limit_status() )



