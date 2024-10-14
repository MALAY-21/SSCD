%{
#include<stdio.h>
extern int yylex();
extern int yywrap();
extern int yyparse();
extern int yyerror(char *str);
%}

%token WH IF DO FOR OP CP OCB CCB CMP SC ASG ID NUM COMMA OPR

%%
start: sif;
sif: IF OP cmpn CP stmt   {printf("VALID STATEMENT IF\n");};
cmpn: ID CMP ID | ID CMP NUM;
stmt: ID ASG ID OPR ID SC | ID ASG ID OPR NUM SC | ID ASG NUM OPR ID SC | ID ASG NUM OPR NUM SC | ID ASG ID SC | ID ASG NUM SC;

%%
int yyerror(char *str)
{
printf("%s", str);
}

int main()
{
yyparse();
return 1;
}
