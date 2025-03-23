//
// Created by Andra Mateș on 15.03.2025.
//
#include "list.h"
#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>


List* createList(int capacity, DestroyFunc destroyFunc, CopyFunc copyFunc) {
    List* list = (List*)malloc(sizeof(List));
    if (list == NULL) return NULL;

    list->length = 0;
    list->capacity = capacity;
    list->destroyFunc = destroyFunc;
    list->copyFunc = copyFunc;

    list->elems = (TElem*)malloc(sizeof(TElem) * capacity);
    if (list->elems == NULL) {
        free(list);
        return NULL;
    }
    return list;
}

void destroyList(List* list) {
    if (list == NULL) return;
    if (list->elems != NULL) {
        for (int i = 0; i < list->length; i++) {
            if (list->elems[i] != NULL) {
                list->destroyFunc(list->elems[i]);
                list->elems[i] = NULL;
            }
        }
        free(list->elems);
        list->elems = NULL;
    }
    free(list);
}

bool resize(List* list) {
    if (list == NULL) return false;
    list->capacity *= 2;

    TElem* aux = (TElem*)malloc(sizeof(TElem) * list->capacity);
    if (aux == NULL) return false;

    for (int i = 0; i < list->length; i++) aux[i] = list->elems[i];
    free(list->elems);
    list->elems = aux;
    return true;
}

void add(List* list, TElem elem) {
    if (list == NULL) return;
    if (list->elems == NULL) return;
    if (list->length >= list->capacity) resize(list);
    list->elems[list->length++] = elem;
}

void delete(List* list, int pos) {
    if (list == NULL) return;
    if (list->elems == NULL) return;
    if (pos < 0 || pos >= list->length) return;
    list->destroyFunc(get(list, pos));
    for (int i = pos; i <list->length - 1; i++) list->elems[i] = list->elems[i + 1];
    list->length--;
}

int getLength(List* list) {
    if (list == NULL) return -1;
    return list->length;
}

int getCapacity(List* list) {
    if (list == NULL) return -1;
    return list->capacity;
}

TElem get(List* list, int pos) {
    return list->elems[pos];
}

List* copyList(List* list) {
    if (list == NULL) return NULL;
    List* copy = createList(getCapacity(list), list->destroyFunc, list->copyFunc);
    copy->length = getLength(list);
    copy->capacity = getCapacity(list);
    for (int i = 0; i < copy->length; i++) copy->elems[i] = list->copyFunc(list->elems[i]);
    return copy;
}