%{
#include<stdio.h>
int i=0,m=0;
struct symtab
{
char name[200];
char type[200];
}sym[20];

%}
ws [\t]
Letters [a-zA-Z]
Digits [0-9]
id {Letters}({Letters}|{Digits})*
%%
#include<{id}\.h> {
strcpy(sym[m].name,yytext);
strcpy(sym[m].type,"Preprocessor directive");
m++;
}
#define{ws}+{id}+({Letters}|{Digits})* {printf("Pre-processor directive is: %s, ", yytext);
strcpy(sym[m].name,yytext);
strcpy(sym[m].type,"Preprocessor directive");
m++;
}
int|float|string|char|double|real {printf("\nThis is datatype: %s",yytext);
strcpy(sym[m].name,yytext);
strcpy(sym[m].type,"Datatype");
m++;}
if|for|then|while|do|in|range {printf("\nThis is keyword: %s",yytext);
strcpy(sym[m].name,yytext);
strcpy(sym[m].type,"Conditional statement");
m++;}
[-+*/] {printf("\nThis is arithmatic operator:%s",yytext);
strcpy(sym[m].name,yytext);
strcpy(sym[m].type,"Arithmatic operator");
m++;}
("<"|">"|"=="|"!="|"<="|">=") {printf("\n This is relational operator:%s",yytext);
strcpy(sym[m].name,yytext);
strcpy(sym[m].type,"Relational operator");
m++;}
("=") {printf("\nThis is assignment operator:%s",yytext);
strcpy(sym[m].name,yytext);
strcpy(sym[m].type,"Memory");
m++;}
"calloc"|"malloc"|"free" {printf("\nThis is memory management keyword:%s",yytext);
strcpy(sym[m].name,yytext);
strcpy(sym[m].type,"Preprocessor directive");
m++;}
"&&"|"||"|"!" {printf("\nThis is logical operator:%s",yytext);
strcpy(sym[m].name,yytext);
strcpy(sym[m].type,"Logical operator");
m++;}
int|float|string|char|double|real"["{Digits}*"]" {printf("\nThis is Array:%s",yytext);
strcpy(sym[m].name,yytext);
strcpy(sym[m].type,"Array");
m++;}
{Digits}*(\.{Digits}*)? {printf("\nThis is arithmatic number:%s",yytext);
strcpy(sym[m].name,yytext);
strcpy(sym[m].type,"Arithmatic");
m++;}
"switch" {printf("\nThis is switch case:%s",yytext);
strcpy(sym[m].name,yytext);
strcpy(sym[m].type,"Switch");
m++;}
"true"|"false" {printf("The boolean keyword is: %s, ", yytext);
strcpy(sym[m].name,yytext);
strcpy(sym[m].type,"Boolean");
m++;}
[a-zA-Z_][a-zA-Z_0-9]*\[[0-9]+\] {printf("Array indexing is: %s, ", yytext);
strcpy(sym[m].name,yytext);
strcpy(sym[m].type,"Array");
m++;}

({Letters}|{Digits})* {printf("\nThis is identifier:%s",yytext);}
%%
int main()
{
yyin=fopen("c.txt","r");
yyout=fopen("cop.txt","w+");
yylex();
fprintf(yyout,"\n\n\n\t\t\n------------------------------------------------------------------\n");
fprintf(yyout,"\tIndex\t\t\tSymbol\t\t\tType");
for(int i=0;i<m;i++){
fprintf(yyout,"\n\t%d",i+1);
fprintf(yyout,"\t\t\t%s",sym[i].name);
fprintf(yyout,"\t\t\t%s",sym[i].type);
}
return 0;
}
int yywrap()
{
return 1;
}