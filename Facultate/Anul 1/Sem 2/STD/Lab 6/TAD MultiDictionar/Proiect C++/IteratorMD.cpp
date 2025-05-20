#include "IteratorMD.h"
#include <exception>

using namespace std;
//Complexitate:best case:theta(1),worst case:theta(n),average:theta(1),overall:O(n)
IteratorMD::IteratorMD(const MD& _md) : md(_md) {
	prim();
}
//Complexitate:best case:theta(1),worst case:theta(n),average:theta(1),overall:O(n)
void IteratorMD::prim() {
	pozCurenta = 0;
	valIndex = 0;
	while (pozCurenta < md.capacitate && (!md.tabela[pozCurenta].occupied || md.tabela[pozCurenta].deleted))
		pozCurenta++;
}
//Complexitate:Theta(1)
bool IteratorMD::valid() const {
	return pozCurenta < md.capacitate && md.tabela[pozCurenta].occupied && !md.tabela[pozCurenta].deleted && valIndex < md.tabela[pozCurenta].values.size();
}
//Complexitate:Theta(1)
TElem IteratorMD::element() const {
	if (!valid())
		throw exception();
	return make_pair(md.tabela[pozCurenta].key, md.tabela[pozCurenta].values[valIndex]);
}
//Complexitate:best case-Theta(1),worst case-Theta(n),average-Theta(1),overall-O(n)
void IteratorMD::urmator() {
	if (!valid())
		throw exception();

	valIndex++;
	if (valIndex >= md.tabela[pozCurenta].values.size()) {
		pozCurenta++;
		valIndex = 0;
		while (pozCurenta < md.capacitate && (!md.tabela[pozCurenta].occupied || md.tabela[pozCurenta].deleted))
			pozCurenta++;
	}
}
