#pragma once
/**
 * struct Errors - enum pentru errori
 */
typedef enum {
    SUCCES = 0,
    TIP_INVALID = 1<<0,
    SUPRAFATA_INVALIDA = 1<<1,
    ADRESA_INVALIDA = 1<<2,
    PRET_INVALID = 1<<3,
    REPO_ERROR = 1<<4
}Errors;

