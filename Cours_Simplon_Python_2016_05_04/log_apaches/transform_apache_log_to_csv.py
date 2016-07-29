import sys
import re

## 89.251.55.16 - - [17/Mar/2015:11:23:36 -0400] "GET / HTTP/1.1" 200 39665 "-" "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:35.0) Gecko/20100101 Firefox/35.0"
#request_line_re = re.compile( '(?P<ip_address>[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}) - - \[(?P<apache_date>[^\]]*)\] "(?P<request>[^"]*)" (?P<return_code>[0-9]{3}).*' )
request_line_re = re.compile( '(?P<ip_address>[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}) - - \[(?P<apache_date>[^\] ]*) (?P<apache_tz>-?\+?[0-9]{4})\] "(?P<request>[A-Z]+ /[^"\?]*)(?P<request_args>[^"]*)(?P<HTTP_version>HTTP[^"]*)" (?P<return_code>[0-9]{3}) (?P<data_size>[0-9]*|-) "(?P<referer>[^"]*)" "(?P<client>[^"]*)".*' )

#print( dir( request_line_re ) )
#print( request_line_re.groups )
print( request_line_re.groupindex )

def write_column_headers( output_file ):
    for k, v in sorted( request_line_re.groupindex.items(), key = lambda x:x[1] ):
        output_file.write( k + (";" if v != request_line_re.groups else "\n") )

def write_log_line_as_csv( output_file, re_match ):
    for it in range(1, request_line_re.groups + 1 ):
        output_file.write( re_match.group(it).replace(";",",") + (";" if it != request_line_re.groups else "\n") )

def main( filename ):
    print( "Opening file {0} ...".format( filename ) )
    print( "Writing to file {0} ...".format( filename + ".csv" ) )

    with open( filename, 'r' ) as input_file:
        with open( filename + ".csv", 'w' ) as output_file:
            write_column_headers( output_file )
            with open( filename + ".reject", 'w' ) as reject_file :
                for it_line in input_file:
                    if it_line.startswith("<br/>"):
                        it_line = it_line[5:]
                    re_match = request_line_re.match( it_line )
                    if re_match:
                        write_log_line_as_csv( output_file, re_match )
                    else:
                        reject_file.write( it_line ) 

if __name__ == '__main__':
    if len( sys.argv ) < 2:
        filename = "log_aggreges.txt"
    else:
        filename = sys.argv[1]
    main( filename )

