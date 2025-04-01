#include "Dictionar.h"
#include <iostream>
#include "IteratorDictionar.h"

Dictionar::Dictionar() {
	capacity=10;
	size=0;
	e=new TElem[capacity];

}
//Toate 3:Theta(n)
Dictionar::~Dictionar() {
	delete[] e;
}
/*
 *Caz fav:Theta(1)
 *Caz defav:Theta(n)
 *Caz mediu:Theta(n)
 */
TValoare Dictionar::adauga(TCheie c, TValoare v){
	for (int i = 0;i<size;i++) {
		if (e[i].first==c) {
			const int val = e[i].second;
			e[i].second=v;
			return val;
		}
	}
	if (size == capacity) {
		capacity *= 2;
		realloc(capacity);
	}
	e[size].first=c;
	e[size].second=v;
	size++;
	return NULL_TVALOARE;
}



//cauta o cheie si returneaza valoarea asociata (daca dictionarul contine cheia) sau null
/*
 *Caz fav:Theta(1)
 *Caz defav:Theta(n)
 *Caz mediu:Theta(n)
 *Caz general:O(n)
 */
TValoare Dictionar::cauta(TCheie c) const{
	for (int i = 0; i < size; i++) {
		if (e[i].first == c) {
			return e[i].second;
		}
	}
	return NULL_TVALOARE;
}

/*
 *Caz fav:Theta(n)
 *Caz defav:Theta(n)
 *Caz mediu:Theta(n)
 */
TValoare Dictionar::sterge(TCheie c){
	for (int i = 0;i<size;i++) {
		if (e[i].first==c) {
			const int val = e[i].second;
			for (int j = i;j<size-1;j++) {
				e[j]=e[j+1];
				
			}
			size--;
			return val;
		}
	}
	return NULL_TVALOARE;
}

/*
 *Caz fav:Theta(1)
 *Caz defav:Theta(1)
 *Caz mediu:Theta(1)
 */
int Dictionar::dim() const {
	return size;
}
/*
 *Caz fav:Theta(1)
 *Caz defav:Theta(1)
 *Caz mediu:Theta(1)
 */
bool Dictionar::vid() const{
	return size==0;
}


IteratorDictionar Dictionar::iterator() const {
	return  IteratorDictionar(*this);
}


