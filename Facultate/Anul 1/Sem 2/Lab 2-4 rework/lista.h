#pragma once
#define INIT_CAPACITY 10

typedef void* TElem;
typedef void(*DestroyFn)(TElem);
typedef TElem(*CopyFn)(TElem);
typedef struct {
    int length;
    int capacity;
    TElem* elems;
    DestroyFn destroyFn;
    CopyFn copyFn;

}List;
List* createList(const DestroyFn destroyFn, const CopyFn copyFn);
void destroyList(List* l);
void resizeList(List* l);
void addElem(List* l, TElem elem);
void deleteElem(List* l, int poz);
void setElem(const List* l, int poz, TElem elem);
TElem getElem(const List* l, int poz);
int getLength(const List* l);
void testList();

