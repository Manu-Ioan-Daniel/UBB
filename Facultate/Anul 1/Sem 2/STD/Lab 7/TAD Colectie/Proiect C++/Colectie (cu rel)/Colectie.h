#pragma once
#include <utility>

typedef int TElem;
typedef bool(*Relatie)(TElem, TElem);

// declarația relației globale
bool rel(TElem, TElem);

class IteratorColectie;

// Structura nodului pentru ABC (element, frecventa)
struct Nod {
	TElem element;
	int frecventa;
	Nod* stanga;
	Nod* dreapta;

	Nod(TElem e, int f = 1) : element(e), frecventa(f), stanga(nullptr), dreapta(nullptr) {}
};

class Colectie {

	friend class IteratorColectie;

private:
	Nod* radacina;
	int dimensiune;

public:
	//constructorul implicit
	Colectie();

	//adauga un element in colectie
	void adauga(TElem e);

	//sterge o aparitie a unui element din colectie
	//returneaza adevarat daca s-a putut sterge
	bool sterge(TElem e);

	//verifica daca un element se afla in colectie
	bool cauta(TElem elem) const;

	//returneaza numar de aparitii ale unui element in colectie
	int nrAparitii(TElem elem) const;

	//intoarce numarul de elemente din colectie (cu tot cu frecvențe)
	int dim() const;

	//verifica daca colectia e vida
	bool vida() const;

	//returneaza un iterator pe colectie
	IteratorColectie iterator() const;

	// destructorul colectiei
	~Colectie();
};
