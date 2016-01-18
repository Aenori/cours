pandoc --latex-engine=xelatex -t beamer $1 -V theme:Warsaw -o ./pdf/`basename $1 .txt`.pdf
