#include "TestScurt.h"
#include <assert.h>
#include "Matrice.h"
#include <iostream>

using namespace std;

void testAll() { //apelam fiecare functie sa vedem daca exista
	Matrice m(4,4);
	assert(m.nrLinii() == 4);
	assert(m.nrColoane() == 4);
	//adaug niste elemente
	m.modifica(1,1,5);
	assert(m.element(1,1) == 5);
	m.modifica(1,1,6);
	assert(m.element(1,2) == NULL_TELEMENT);
	m.redimensioneaza(5,5);
	assert(m.nrColoane() == 5);
	assert(m.nrLinii() == 5);
	m.modifica(4,4,3);
	assert(m.element(4,4) == 3);
	m.redimensioneaza(2,3);
	assert(m.element(1,1) == 6);
	try {
		m.element(4,4);
		assert(false);
	}catch (exception& e) {
		assert(true);
	}

}
