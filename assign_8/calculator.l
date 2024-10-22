%{
#include <stdlib.h>
#include "y.tab.h"
void yyerror(const char *error);
%}

%%
sin {return SIN;}
cos {return COS;}
cube {return CUBE;}
square {return SQUARE;}
tan {return TAN;}
sqrt {return SQRT;}
[0-9]+      { yylval.intval = atoi(yytext); return NUMBER; }
[a-zA-Z]+   { yylval.fchar = yytext; return NAME; }
[ \t]+      {}
\n          { return 0; }
.           { return yytext[0]; }

%%

int yywrap() {
    return 1;
}
