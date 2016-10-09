import sys

class StdoutFilledWithJoy:
    def write( self, x ):
        if x != "\n":
            sys.__stdout__.write("Il y a de la joie !\n")
        sys.__stdout__.write( x )

    def flush( self ):
        sys.__stdout__.flush()

sys.stdout = StdoutFilledWithJoy()

print( "Les chiffres du chomage ne sont pas terribles" )


