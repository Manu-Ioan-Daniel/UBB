#include "IteratorDictionar.h"
#include "Dictionar.h"

using namespace std;
/*
 * Caz favorabil: Theta(1)
 * Caz defavorabil: Theta(1)
 * Caz mediu: Theta(1)
 */
IteratorDictionar::IteratorDictionar(const Dictionar& d) : dict(d){
	current=0;
}

/*
 * Caz favorabil: Theta(1)
 * Caz defavorabil: Theta(1)
 * Caz mediu: Theta(1)
 */
void IteratorDictionar::prim() {
	current=0;
}

/*
 * Caz favorabil: Theta(1)
 * Caz defavorabil: Theta(1)
 * Caz mediu: Theta(1)
 */
void IteratorDictionar::urmator() {
	current+=1;
}

/*
 * Caz favorabil: Theta(1)
 * Caz defavorabil: Theta(1)
 * Caz mediu: Theta(1)
 */
TElem IteratorDictionar::element() const{
	return dict.e[current];
}

/*
 * Caz favorabil: Theta(1)
 * Caz defavorabil: Theta(1)
 * Caz mediu: Theta(1)
 */
bool IteratorDictionar::valid() const {
	return current<dict.size;

}

