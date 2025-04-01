#include "Dictionar.h"
#include <iostream>
#include "IteratorDictionar.h"

Dictionar::Dictionar() {
	capacity=10;
	size=0;
	e=new TElem[capacity];

}

Dictionar::~Dictionar() {
	delete[] e;
}

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
TValoare Dictionar::cauta(TCheie c) const{
	for (int i = 0; i < size; i++) {
		if (e[i].first == c) {
			return e[i].second;
		}
	}
	return NULL_TVALOARE;
}


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


int Dictionar::dim() const {
	return size;
}

bool Dictionar::vid() const{
	return size==0;
}


IteratorDictionar Dictionar::iterator() const {
	return  IteratorDictionar(*this);
}


