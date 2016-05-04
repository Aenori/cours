pandoc --latex-engine=xelatex -t beamer --number-sections $1 -o ./pdf/`basename $1 .txt`.pdf
