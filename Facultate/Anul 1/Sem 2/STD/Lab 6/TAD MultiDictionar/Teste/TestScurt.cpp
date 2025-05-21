#include "TestScurt.h"
#include "MD.h"
#include "IteratorMD.h"
#include <assert.h>
#include <vector>
#include<iostream>
void testAdaugaInexistente();
void testAll() {
	MD m;
	m.adauga(1, 100);
	m.adauga(2, 200);
	m.adauga(3, 300);
	m.adauga(1, 500);
	m.adauga(2, 600);
	m.adauga(4, 800);

	assert(m.dim() == 6);

	assert(m.sterge(5, 600) == false);
	assert(m.sterge(1, 500) == true);

	assert(m.dim() == 5);

    vector<TValoare> v;
	v=m.cauta(6);
	assert(v.size()==0);

	v=m.cauta(1);
	assert(v.size()==1);

	assert(m.vid() == false);

	IteratorMD im = m.iterator();
	assert(im.valid() == true);
	while (im.valid()) {
		im.element();
		im.urmator();
	}
	assert(im.valid() == false);
	im.prim();
	assert(im.valid() == true);
	testAdaugaInexistente();
}


void testAdaugaInexistente() {
	MD md1;
	MD md2;


	md1.adauga(1, 10);
	md1.adauga(2, 20);

	md2.adauga(2, 20); // Cheie și valoare deja existente în md1
	md2.adauga(2, 30); // Cheie existentă, valoare nouă
	md2.adauga(3, 40); // Cheie și valoare noi
	const int added = md1.adaugaInexistente(md2);
	assert(added == 2);
	assert(md1.cauta(2).size() == 2);
	assert(md1.cauta(3).size() == 1);
	assert(md1.dim() == 4);

	std::cout << "Test adaugaInexistente passed!" << std::endl;
}