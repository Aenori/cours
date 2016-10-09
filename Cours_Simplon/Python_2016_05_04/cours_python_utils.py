import re
import traceback

print_re = re.compile( "\n[ \t]*print *\(" )

def test_with_expected( self, f, args, expected ):
    if not isinstance( args, tuple ):
        args = (args,)
    try:
        res = f( *args )
    except Exception as e:
        self.fail( "L'appel de votre fonction a échoué avec les arguments {0} et l'exception {1}\n{2}".format(args, e, traceback.format_exc() ) ) 
    self.assertEqual( res, expected, "Avec {0} en entrée, votre fonction devrait renvoyer {1}, elle a renvoyé {2}".format(args, expected, res) )

def warning_on_print( In ):
    if print_re.search( In[-1] ):
        print( "="*46 )
        print( " WARNING : vous n'avez pas besoin de print " )
        print( "="*46 )

class LimitedAccessList:
    def __init__(self, liste, limite ):
        self.liste = liste
        self.limite = limite
        self.counter = 0 

    def reset_count(self):
        self.counter = 0 

    def _check_counter(self):
        if self.counter > self.limite:
            raise Exception("\n{1}\nErreur : vous travaillez avec une liste à accès limité, vous ne pouvez accéder que {0} fois consécutivement à la liste\n{1}\n".format( self.limite, "="*25 ) ) 

    def __getitem__( self, i ):
        self.counter += 1
        self._check_counter()
        return self.liste[i]

    def __iter__( self ):
        for it in self.liste:
            self.counter += 1
            self._check_counter()
            yield it

    def __len__(self):
        return len(self.liste)
