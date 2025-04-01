#pragma once
#define INIT_CAPACITY 10
/**
 * definim TElem ca si void*
 */
typedef void* TElem;
/**
 * definim DestroyFn ca si void(*)(TElem)
 * cu DestroyFn putem distruge un TElem
 */
typedef void(*DestroyFn)(TElem);

/**
 * definim copyFn ca si TElem(*)(TElem)
 * returneaza un deepcopy al unui TElem
 */
typedef TElem(*CopyFn)(TElem);

/**
 * structura de lista
 */
typedef struct {
    int length;
    int capacity;
    TElem* elems;
    DestroyFn destroyFn;
    CopyFn copyFn;

}List;

/**
 * @param destroyFn destructorul
 * @param copyFn constructorul
 * @return o lista goala
 */
List* createList(const DestroyFn destroyFn, const CopyFn copyFn);

/**
 * functie care distruge o lista
 * @param l - o lista
 */
void destroyList(List* l);

/**
 * functie care dubleaza capacitatea unei liste
 * @param l o lista
 */
void resizeList(List* l);

/**
 * functie care adauga un element intr o lista
 * @param l o lista
 * @param elem un TElem
 */
void addElem(List* l, TElem elem);

/**
 * functie care sterge un element de pe o pozitie dintr o lista
 * @param l o lista
 * @param poz pozitia de pe care vrem sa stergem elementul
 */
void deleteElem(List* l, int poz);

/**
 * functie care modifica elementul din lista de pe pozitia poz la elem
 * @param l o lista
 * @param poz pozitia elementului pe care il modificam
 * @param elem un element
 */

void setElem(const List* l, int poz, TElem elem);

/**
 * functie care returneaza elementul de pe pozitia poz dintr o lista
 * @param l lista
 * @param poz pozitia elementului pe care il dorim
 * @return elementul de pe pozitia poz
 */
TElem getElem(const List* l, int poz);

/**
 * functie care returneaza dimensiunea listei
 * @param l lista
 * @return dimensiunea listei
 */
int getLength(const List* l);

/**
 * functie de test pentru lista.c
 */
void testList();

