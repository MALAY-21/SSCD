%{
#include <math.h>
#include "y.tab.h"
%}

%%

[0-9]+|[0-9]*\.[0-9]+ {
    yylval.p = atof(yytext);
    return NUM;
}

sin {return SIN;}
cos {return COS;}
cube return CUBE;
square return SQUARE;
tan return TAN;
sqrt return SQRT;

[/t];

\n return 0;
. return yytext[0];

%%
