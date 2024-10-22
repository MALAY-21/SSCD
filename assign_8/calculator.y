%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

void yyerror(const char *s);
int yylex();
%}

%union {
    char* fchar;
    double fval;
    int intval;
}

%token <fchar> NAME
%token <intval> NUMBER
%token SIN COS TAN SQUARE SQRT CUBE
%type <fval> exp

%left '+' '-'
%left '*' '/'

%%

stmt:
    NAME '=' exp { printf("stmt: %s = %f\n", $1, $3); }
    | exp { printf("exp = %f\n", $1); }
    ;

exp:
    exp '+' exp { $$ = $1 + $3; }
    | exp '-' exp { $$ = $1 - $3; }
    | exp '*' exp { $$ = $1 * $3; }
    | exp '/' exp {
        if ($3 == 0) {
            yyerror("Division by zero");
            $$ = 0; // Handle division by zero
        } else {
            $$ = $1 / $3;
        }
    }
    | SIN '(' exp ')' { $$ = sin($3 * 3.14 / 180); }
    | COS '(' exp ')' { $$ = cos($3 * 3.14 / 180); }
    | TAN '(' exp ')' { $$ = tan($3 * 3.14 / 180); }
    | SQUARE '(' exp ')' { $$ = $3 * $3; }
    | SQRT '(' exp ')' { $$ = sqrt($3); }
    | CUBE '(' exp ')' { $$ = $3 * $3 * $3; }
    | NUMBER { $$ = $1; }
    ;
%%

void yyerror(const char *s) {
    fprintf(stderr, "Error: %s\n", s);
}

int main() {
    printf("Enter expressions :\n");
    yyparse();
    return 0;
}
