#include "IteratorLP.h"
#include "Lista.h"
#include <exception>

//numele functiilor si complexitatile lor (best case, worst case, average, overall)
//IteratorLP::IteratorLP - Complexitate:best case:Theta(1), worst case:Theta(1), average:Theta(1), overall:Theta(1)
//IteratorLP::prim - Complexitate:best case:Theta(1), worst case:Theta(1), average:Theta(1), overall:Theta(1)
//IteratorLP::urmator - Complexitate:best case:Theta(1), worst case:Theta(1), average:Theta(1), overall:Theta(1)
//IteratorLP::valid - Complexitate:best case:Theta(1), worst case:Theta(1), average:Theta(1), overall:Theta(1)
//IteratorLP::element - Complexitate:best case:Theta(1), worst case:Theta(1), average:Theta(1), overall:Theta(1)
//IteratorLP::avanseazaKPasi - Complexitate:best case:Theta(1), worst case:Theta(1), average:Theta(1), overall:Theta(1)
IteratorLP::IteratorLP(const Lista& lista):current(lista.head), lista(lista) {

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
/*pseudocod:
 *preconditii:k este un numar mai mare decat 0
 *postconditii:iterator.current<-iterator.current+k
 *arunca exceptie daca iteratorul nu e valid sau k nu este mai mare decat 0
 functie avanseazaKPasi(IteratorLP iterator,intreg k)
	daca(!valid(iterator) || k<0) atunci
		arunca exceptie
	sf.daca
	cat timp(k!=0) executa
		iterator.current=iterator.lista.next[iterator.current]
		k--
    sf.cat timp
 sf.functie
*/
//IteratorLP::avanseazaKPasi - Complexitate:best case:Theta(k), worst case:Theta(k), average:Theta(k), overall:O(k)
void IteratorLP::avanseazaKPasi(int k) {
	if (!valid() || k<0) {
		throw std::exception();
	}
	if (current+k>lista.size) {
        current=-1;
		return;
    }
	while(k!=0){
        current=lista.next[current];
		k--;
	}

}


