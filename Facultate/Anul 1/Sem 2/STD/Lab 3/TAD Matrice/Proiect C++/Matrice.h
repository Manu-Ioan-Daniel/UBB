#pragma once
#include "LinkedList.h"
typedef int TElem;


#define NULL_TELEMENT 0

typedef struct {
	int linie,coloana;
	TElem value;
}triplet;
class Matrice {
	int rows;
	int columns;
	LinkedList<triplet> elems;

public:

	//constructor
	//se arunca exceptie daca nrLinii<=0 sau nrColoane<=0
	Matrice(int nrLinii, int nrColoane);


	//destructor
	~Matrice(){};

	//returnare element de pe o linie si o coloana
	//se arunca exceptie daca (i,j) nu e pozitie valida in Matrice
	//indicii se considera incepand de la 0
	TElem element(int i, int j) const;


	// returnare numar linii
	int nrLinii() const;

	// returnare numar coloane
	int nrColoane() const;


	// modificare element de pe o linie si o coloana si returnarea vechii valori
	// se arunca exceptie daca (i,j) nu e o pozitie valida in Matrice
	TElem modifica(int i, int j, TElem);
	// redimensionează o matrice la o anumită dimensiune. În cazul în care dimensiunile sunt mai mici decât
	// dimensiunile actuale, elemente de pe pozițiile care nu mai sunt existente vor dispărea. În cazul în care
	// dimensiunile sunt mai mari decât dimensiunile actuale, toate elementele noi (de pe poziții anterior
	// inexistente) vor fi NULL_TELEM.
	// aruncă excepție în cazul în care noile dimensiuni sunt negative
	void redimensioneaza(int numarNouLinii,int numarNouColoane);

};







