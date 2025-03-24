//
// Created by Andra Mateș on 15.03.2025.
//

#ifndef LIST_H
#define LIST_H

#define CAPACITY 10

typedef void* TElem;
typedef void (*DestroyFunc)(TElem);
typedef TElem (*CopyFunc)(TElem);

typedef struct {
    TElem* elems;
    int length;
    int capacity;
    DestroyFunc destroyFunc;
    CopyFunc copyFunc;
} List;

/**
 * Creează o listă dinamică.
 * @param capacity Capacitatea inițială a listei.
 * @param destroyFunc Funcția care va fi folosită pentru a elibera memoria fiecărui element.
 * @param copyFunc Funcția pentru copierea unui element.
 * @return Pointer la lista creată.
 */
List* createList(int capacity, DestroyFunc destroyFunc, CopyFunc copyFunc);

/**
 * Distruge o listă și eliberează memoria alocată.
 * @param list Lista care trebuie distrusă.
 */
void destroyList(List* list);

/**
 * Adaugă un element în listă.
 * @param list Lista în care se adaugă elementul.
 * @param elem Elementul care va fi adăugat.
 */
void add(List* list, TElem elem);

/**
 * Șterge un element din listă de pe o anumită poziție.
 * @param list Lista din care se șterge elementul.
 * @param pos Poziția elementului care va fi șters (index 0-based).
 */
void delete(List* list, int pos);

/**
 * Returnează lungimea listei (numărul actual de elemente).
 * @param list Lista asupra căreia se face interogarea.
 * @return Numărul de elemente din listă.
 */
int getLength(List* list);

/**
 * Returnează capacitatea maximă curentă a listei.
 * @param list Lista asupra căreia se face interogarea.
 * @return Capacitatea listei.
 */
int getCapacity(List* list);

/**
 * Returnează elementul de pe o anumită poziție din listă.
 * @param list Lista din care se obține elementul.
 * @param pos Poziția elementului dorit (index 0-based).
 * @return Elementul de pe poziția respectivă.
 */
TElem get(List* list, int pos);


/**
 * Creează o copie a listei.
 * @param list Lista care trebuie copiată.
 * @return O nouă listă identică cu lista inițială.
 */
List* copyList(List* list);

#endif //LIST_H
