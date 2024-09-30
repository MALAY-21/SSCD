#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>

char input[100];
int i = 0;

void E();
void Eprime();
void T();
void Tprime();
void F();

void E() {
    printf("Entering E()\n");
    T();
    Eprime();
    printf("Exiting E()\n");
}

void Eprime() {
    printf("Entering Eprime()\n");
    while (input[i] == '+') {
        i++; // Consume '+'
        printf("Found '+'\n");
        T();
    }
    printf("Exiting Eprime()\n");
}

void T() {
    printf("Entering T()\n");
    F();
    Tprime();
    printf("Exiting T()\n");
}

void Tprime() {
    printf("Entering Tprime()\n");
    while (input[i] == '*') {
        i++; // Consume '*'
        printf("Found '*'\n");
        F();
    }
    printf("Exiting Tprime()\n");
}

void F() {
    printf("Entering F()\n");
    if (input[i] == '(') {
        i++; // Consume '('
        E();
        if (input[i] != ')') {
            printf("Error: Missing closing parenthesis\n");
            exit(1); // Exit on error
        }
        i++; // Consume ')'
    } else if (strncmp(&input[i], "id", 2) == 0) {
        printf("Found identifier 'id'\n");
        i += 2; // Move index past "id"
    } else if (isalnum(input[i])) {
        // Handle single-letter identifiers or numbers
        printf("Found identifier '%c'\n", input[i]);
        i++;
    } else {
        printf("Error: Invalid identifier\n");
        exit(1); // Exit on error
    }
    printf("Exiting F()\n");
}

int main() {
    printf("Enter the arithmetic expression: ");
    scanf("%s", input);

    // Append the end marker to the input
    strcat(input, "$");

    E();

    // Check for the end marker after parsing
    if (input[i] == '$') {
        printf("Expression Accepted\n");
    } else {
        printf("Error: Expression not accepted\n");
    }

    return 0;
}