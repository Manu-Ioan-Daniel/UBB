#include "IteratorColectie.h"
#include "Colectie.h"
#include <stack>
#include <stdexcept>

using namespace std;

IteratorColectie::IteratorColectie(const Colectie& c) : col(c) {
	prim();
}

void IteratorColectie::prim() {
	while (!stiva.empty()) stiva.pop();
	Nod* p = col.radacina;
	while (p != nullptr) {
		stiva.emplace(p, 0);
		p = p->stanga;
	}
}

void IteratorColectie::urmator() {
	if (!valid()) throw runtime_error("Iterator invalid");

	auto& top = stiva.top();
	top.second++; // frecvența curentă

	if (top.second < top.first->frecventa) return;

	Nod* p = top.first->dreapta;
	stiva.pop();
	while (p != nullptr) {
		stiva.emplace(p, 0);
		p = p->stanga;
	}
}

bool IteratorColectie::valid() const {
	return !stiva.empty();
}

TElem IteratorColectie::element() const {
	if (!valid()) throw runtime_error("Iterator invalid");
	return stiva.top().first->element;
}
