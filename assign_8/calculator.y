%{
#include <math.h>
#include <stdio.h>
#include <stdlib.h>

void yyerror(char *);
int yylex(void);
%}

%union { double p; }

%token <p> NUM
%token SIN COS TAN SQUARE CUBE SQRT

%right '='
%left '+' '-'
%left '*' '/'
%nonassoc uminu
%type <p> expr

%%

start: expr { printf("Result = %g\n", $1); };

expr: expr '+' expr { $$ = $1 + $3; }
    | expr '-' expr { $$ = $1 - $3; }
    | expr '*' expr { $$ = $1 * $3; }
    | expr '/' expr { if ($3 == 0) {
                        printf("Divide by Zero\n");
                        exit(0);
                    } else $$ = $1 / $3; }
    | '-' expr %prec uminu { $$ = -$2; }
    | '(' expr ')' { $$ = $2; }
    | SIN '(' expr ')' { $$ = sin($3 * 3.14 / 180); }
    | COS '(' expr ')' { $$ = cos($3 * 3.14 / 180); }
    | TAN '(' expr ')' { $$ = tan($3 * 3.14 / 180); }
    | SQUARE '(' expr ')' { $$ = $3 * $3; }
    | SQRT '(' expr ')' { $$ = sqrt($3); }
    | CUBE '(' expr ')' { $$ = $3 * $3 * $3; }
    | NUM { $$ = $1; };

%%

void main() {
    do {
        printf("Enter the Expression: ");
        yyparse();
    } while (1);
}

int yywrap() {
    return 1;
}

void yyerror(char *s) {
    printf("%s\n", s);
    exit(0);
}
