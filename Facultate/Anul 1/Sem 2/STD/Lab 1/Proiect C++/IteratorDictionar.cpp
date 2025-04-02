#include "IteratorDictionar.h"

#include <exception>

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
	if (!valid()) {
		throw exception();
	}
	current+=1;
}
/*
 * Caz favorabil: Theta(1)
 * Caz defavorabil: Theta(1)
 * Caz mediu: Theta(1)
 */
void IteratorDictionar::avanseazaKPasi(const int k) {
	/*
	Functie avanseazaKPasi(k)
		Daca k <= 0 sau Iteratorul nu este valid executa
			Arunca exceptie
		Sf.daca
	current ← current + k
	SfarsitFunctie
	*/
	if (k<=0 || !valid()) {
		throw exception();
	}
	current+=k;
}

/*
 * Caz favorabil: Theta(1)
 * Caz defavorabil: Theta(1)
 * Caz mediu: Theta(1)
 */
TElem IteratorDictionar::element() const{
	if (!valid()) {
		throw exception();
	}
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

