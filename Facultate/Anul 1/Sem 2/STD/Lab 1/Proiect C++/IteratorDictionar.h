#pragma once
#include "Dictionar.h"

class IteratorDictionar
{
	friend class Dictionar;
private:
	//constructorul primeste o referinta catre Container
	//iteratorul va referi primul element din container
	IteratorDictionar(const Dictionar& d);

	//contine o referinta catre containerul pe care il itereaza
	const Dictionar& dict;
	int current;

public:

		//reseteaza pozitia iteratorului la inceputul containerului
		void prim();

		//muta iteratorul in container
		// arunca exceptie daca iteratorul nu e valid
		void urmator();

		//verifica daca iteratorul e valid (indica un element al containerului)
		bool valid() const;

		//returneaza valoarea elementului din container referit de iterator
		//arunca exceptie daca iteratorul nu e valid
		TElem element() const;

		// mută cursorul iteratorului a.î. să refere al k-lea element începând de la cel curent. Iteratorul devine nevalid
		//în cazul în care există mai puțin de k elemente rămase în dicționar.
		// aruncă excepție în cazul în care iteratorul este nevalid sau k este zero ori negativ.
		void avanseazaKPasi(int k);
};
