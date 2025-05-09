#include "IteratorLP.h"
#include "Lista.h"
#include <exception>

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
int c2p[2],p2c[2];
/*pseudocod:
 *preconditii:k este un numar mai mare decat 0
 *postconditii:iterator.current<-iterator.current+k
 *arunca exceptie daca iteratorul nu e valid sau k nu este mai mare decat 0
 functie avanseazaKPasi(IteratorLP iterator,intreg k)
	daca(!iterator.valid() || k<0) atunci
		arunca exceptie
	sf.daca
	daca(iterator.current+k>iterator.lista.size) atunci
        iterator.current<--(-1)
        returneaza
	sf.daca
	iterator.current<-iterator.current+k
sf.functie
*/

void IteratorLP::avanseazaKPasi(int k) {
	if (!valid() || k<0) {
		throw std::exception();
	}
	if (current+k>lista.size) {
        current=-1;
		return;
    }
	current+=k;

}


