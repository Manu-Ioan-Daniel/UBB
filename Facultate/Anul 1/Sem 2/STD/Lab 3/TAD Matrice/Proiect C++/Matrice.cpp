#include "Matrice.h"

#include <exception>



using namespace std;


Matrice::Matrice(const int nrLinii, const int nrColoane) {
	if (nrLinii<=0 || nrColoane<=0) {
		throw exception();
	}
	rows=nrLinii;
	columns=nrColoane;
}



int Matrice::nrLinii() const{
	return rows;
}


int Matrice::nrColoane() const{
	return columns;
}


TElem Matrice::element(const int i,const int j) const{
	if (i<0 || i>=rows || j<0 || j>=columns) {
        throw exception();
    }
	for (const auto& triplet:elems) {
		if (triplet.linie==i && triplet.coloana==j) {
			return triplet.value;
		}
	}
	return NULL_TELEMENT;
}



TElem Matrice::modifica(const int i, const int j, const TElem e) {
	if (i < 0 || i >= rows || j < 0 || j >= columns)
		throw exception();

	for (auto it = elems.begin(); it != LinkedList<triplet>::end(); ++it) {
		if (it->linie == i && it->coloana == j) {
			const TElem oldValue = it->value;
			if (e == NULL_TELEMENT) {
				elems.erase(it);
			} else {
				it->value = e;
			}
			return oldValue;
		}
		if (it->linie > i || (it->linie == i && it->coloana > j)) {
			if (e != NULL_TELEMENT) {
				const triplet nou{ i, j, e };
				elems.insert(nou, it);
			}
			return NULL_TELEMENT;
		}
	}
	if (e!=NULL_TELEMENT) {
        const triplet nou{ i, j, e };
        elems.push_back(nou);
    }
	return NULL_TELEMENT;
}


