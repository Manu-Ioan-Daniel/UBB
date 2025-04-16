#include "Matrice.h"

#include <exception>



using namespace std;


//Complexitate:fav:Theta(1),defav:Theta(1),average:Theta(1)
Matrice::Matrice(const int nrLinii, const int nrColoane) {
	if (nrLinii<=0 || nrColoane<=0) {
		throw exception();
	}
	rows=nrLinii;
	columns=nrColoane;
}


//Complexitate:toate Theta(1)
int Matrice::nrLinii() const{
	return rows;
}

//Complexitate:toate Theta(1)
int Matrice::nrColoane() const{
	return columns;
}

//Complexitate:fav:Theta(1),defav:Theta(n),average:Theta(n)
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


//Complexitate:fav:Theta(1),defav:Theta(n),average:Theta(n)
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
/*pseudocod:
 *{preconditii:m este o Matrice,numarNouLinii>0,numarNouColoane>0}
 *{postconditii:m.rows=numarNouLinii,m.columns=numarNouColoane}
 *functie redimensioneaza(Matrice m, intreg numarNouLinii, intreg numarNouColoane)
 *		daca(numarNouLinii<=0 sau numarNouColoane<=0) atunci
 *			arunca exceptie
 *		sf.daca
 *		daca(numarNouLinii>=m.rows si numarNouColoane>=m.columns) atunci
 *			m.rows<-numarNouLinii
 *			m.columns<-numarNouColoane
 *		sf.daca
 *		newElems<-LinkedList<triplet>()
 *		pentru fiecare triplet din m.elems executa
 *			daca(triplet.linie<numarNouLinii si triplet.coloana<numarNouColoane) atunci
 *				newElems.push_back(triplet)
 *			sf.daca
 *		sf.pentru
 *		m.rows<-numarNouLinii
 *		m.columns<-numarNouColoane
 *		m.elems<-newElems
 *	sf.functie
 */
//Complexitate:fav:Theta(1),defav:Theta(n),average:Theta(n)
void Matrice::redimensioneaza(const int numarNouLinii,const int numarNouColoane){
    if (numarNouLinii<=0 || numarNouColoane<=0) {
        throw exception();
    }
	if (numarNouLinii>=rows && numarNouColoane>=columns) {
		rows=numarNouLinii;
		columns=numarNouColoane;
		return;
	}
    LinkedList<triplet> newElems;
    for (const auto& triplet:elems) {
        if (triplet.linie<numarNouLinii && triplet.coloana<numarNouColoane) {
            newElems.push_back(triplet);
        }
    }
    rows=numarNouLinii;
    columns=numarNouColoane;
    elems=newElems;
}

