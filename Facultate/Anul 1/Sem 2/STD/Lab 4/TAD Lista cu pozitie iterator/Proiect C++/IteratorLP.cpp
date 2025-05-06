#include "IteratorLP.h"
#include "Lista.h"
#include <exception>

IteratorLP::IteratorLP(const Lista& l):lista(l), current(l.head) {

}

void IteratorLP::prim(){
	current=lista.head;
}

void IteratorLP::urmator(){
	if (!valid()) {
		throw std::exception();
	}
	current=lista.next[current];
}

bool IteratorLP::valid() const{
	return current!=-1;
}

TElem IteratorLP::element() const{
	if (current==-1) {
		throw std::exception();
	}
	return lista.element[current];
}


