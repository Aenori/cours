# -*- coding: utf-8 -*-

from oc_request.twitter_conf import CONSUMER_KEY_2, CONSUMER_SECRET_2, ACCESS_TOKEN_2, ACCESS_TOKEN_SECRET_2
import tweepy
api = None

def get_twitter_api(force_refresh = False):
  global api
  if (api is not None) and (not force_refresh):
    return api

  consumer_key_2 = CONSUMER_KEY_2
  consumer_secret_2 = CONSUMER_SECRET_2
  access_token_2 = ACCESS_TOKEN_2
  access_token_secret_2 = ACCESS_TOKEN_SECRET_2

  auth = tweepy.OAuthHandler(consumer_key_2, consumer_secret_2)
  auth.secure = True
  auth.set_access_token(access_token_2, access_token_secret_2)

  api = tweepy.API(auth)
  return api

try:
  get_twitter_api()
  print( "Twitter api connection ok !" )
except Exception as e:
  print( "Twitter api connection failed with exception :" )
  print( e )

