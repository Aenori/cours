import unittest
import sys
import traceback
import collections
import re
import random
this = sys.modules[__name__]

from cours_python_utils import test_with_expected, warning_on_print, LimitedAccessList

class TestExercice1( unittest.TestCase ):
    def test_code(self):
        warning_on_print(In)

    def test_fonction(self):
        rng = random.Random(0)
        for it in range(1,8):
            test_liste = list(range(it))
            random.shuffle( test_liste )
            test_with_expected( self, maximum, (test_liste,), expected = it - 1 )

class TestExercice2( unittest.TestCase ):
    def test_code(self):
        warning_on_print(In)

    def test_fonction(self):
        rng = random.Random(0)
        for it in range(1,8):
            test_liste = list(range(it))
            random.shuffle( test_liste )
            test_with_expected( self, index_maximum, (test_liste,), expected = test_liste.index( it - 1 ) )

class TestExercice3( unittest.TestCase ):
    def test_code(self):
        warning_on_print(In)

    def test_fonction(self):
        rng = random.Random(0)
        for it in range(1,8):
            test_liste = list(range(it))
            random.shuffle( test_liste )
            for it2 in range(it+1):
                test_with_expected( self, extraire_plus_grand_que, (test_liste,it2), expected = [it for it in test_liste if it > it2] )


class TestExercice4( unittest.TestCase ):
    def test_1( self ):
        test_string = generer_liste_html( ["Vimes", "Rincevent", "HarryKing", "Death", "Vetinari"] ).replace("\t","").replace(" ","").replace("\n","")
        item_list = test_string.split("</li>")
        item_list[:-1] = [it + "</li>" for it in item_list[:-1]]
        self.assertEqual( item_list, [ "<ui><li>Vimes</li>", "<li>Rincevent</li>", "<li>HarryKing</li>", "<li>Death</li>", "<li>Vetinari</li>", "</ui>" ] )


class TestExercice5( unittest.TestCase ):
    def test_code(self):
        warning_on_print(In)

    def test_1( self ):
        test_input_1 = [[10, 20], [10.148, 20.538], [9.169, 20.44], [11.12, 21.302], [9.659, 21.272]]
        test_with_expected( self, hist_une_action, test_input_1, 1, expected = [20, 20.538, 20.44, 21.302, 21.272] )
        test_with_expected( self, hist_une_action, test_input_1, 0, expected = [10,10.148,9.169,11.12,9.659] )
        test_input_2 = [[1, 2, 3], [1.0001, 2.0002, 3.0003], [1.0002, 2.0004, 3.0006], [1.0003, 2.0006, 3.0009] ]
        test_with_expected( self, hist_une_action, test_input_2, 0, expected = [1, 1.0001, 1.0002, 1.0003] )
        test_with_expected( self, hist_une_action, test_input_2, 2, expected = [3, 3.0003, 3.0006, 3.0009] )

def tester_exo( n, globals_dict ):
    for k, v in globals_dict.items():
        setattr( this, k, v )
    launch_test_case( eval( "TestExercice{0}".format(n) ) )

def launch_test_case( test_case ):
  suite = unittest.TestLoader().loadTestsFromTestCase( test_case )
  unittest.TextTestRunner(verbosity=2).run(suite)

