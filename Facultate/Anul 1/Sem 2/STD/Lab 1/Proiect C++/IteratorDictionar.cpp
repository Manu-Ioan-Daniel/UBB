#include "IteratorDictionar.h"
#include "Dictionar.h"

using namespace std;

IteratorDictionar::IteratorDictionar(const Dictionar& d) : dict(d){
	current=0;
}


void IteratorDictionar::prim() {
	current=0;
}


void IteratorDictionar::urmator() {
	current+=1;
}


TElem IteratorDictionar::element() const{
	return dict.e[current];
}


bool IteratorDictionar::valid() const {
	return current<dict.size;

}

