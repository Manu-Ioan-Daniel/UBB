
#include <exception>
#include "IteratorLP.h"
#include "Lista.h"
//numele functiilor si complexitatile lor (best case, worst case, average, overall)
//Lista::Lista - Complexitate:best case:Theta(1), worst case:Theta(1), average:Theta(1), overall:Theta(1)
//Lista::dim - Complexitate:best case:Theta(1), worst case:Theta(1), average:Theta(1), overall:Theta(1)
//Lista::vida - Complexitate:best case:Theta(1), worst case:Theta(1), average:Theta(1), overall:Theta(1)
//Lista::prim - Complexitate:best case:Theta(1), worst case:Theta(1), average:Theta(1), overall:Theta(1)
//Lista::elem - Complexitate:best case:Theta(1), worst case:Theta(1), average:Theta(1), overall:Theta(1)
//Lista::sterge - Complexitate:best case:Theta(1), worst case:Theta(n), average:Theta(n), overall:O(n)
//Lista::cauta - Complexitate:best case:Theta(1), worst case:Theta(n), average:Theta(n), overall:O(n)
//Lista::modifica - Complexitate:best case:Theta(1), worst case:Theta(1), average:Theta(1), overall:Theta(1)
//Lista::adauga - Complexitate:best case:Theta(1), worst case:Theta(n), average:Theta(n), overall:O(n)
//Lista::adaugaInceput - Complexitate:best case:Theta(1), worst case:Theta(1), average:Theta(1), overall:Theta(1)
//Lista::adaugaSfarsit - Complexitate:best case:Theta(1), worst case:Theta(1), average:Theta(1), overall:Theta(1)
//Lista::~Lista - Complexitate:best case:Theta(1), worst case:Theta(1), average:Theta(1), overall:Theta(1)
//Lista::resize - Complexitate:best case:Theta(n), worst case:Theta(n), average:Theta(n), overall:Theta(n)
Lista::Lista() {
	capacity=10;
	element=new TElem[capacity];
	next = new int[capacity];
	head=-1;
	tail=-1;
	firstFree=0;
	size=0;
	for (int i=0; i<capacity-1; i++)
		next[i]=i+1;
	next[capacity-1]=-1;
}

int Lista::dim() const {

	return size;
}


bool Lista::vida() const {
	return size==0;
}

IteratorLP Lista::prim() const {
    return IteratorLP(*this);
}

TElem Lista::elem(IteratorLP poz) const {
	if (!poz.valid()) {
		throw std::exception();
	}
	return element[poz.current];
}

TElem Lista::sterge(IteratorLP& poz) {
	if (!poz.valid()) {
		throw std::exception();
	}
	const int toDelete=poz.current;
	const TElem deletedElement=element[toDelete];
	if (toDelete==head) {
		head=next[toDelete];
	}else {
		int prev=head;
		while (next[prev]!=toDelete) {
			prev=next[prev];
		}
		next[prev]=next[toDelete];
		if (toDelete==tail) {
			tail=prev;
		}
	}
	next[toDelete]=firstFree;
	firstFree=toDelete;
	poz.current=next[toDelete];
	size--;
	return deletedElement;
}

IteratorLP Lista::cauta(TElem e) const{
	IteratorLP it=prim();
	while (it.valid()) {
		if (elem(it)==e) {
			return it;
		}
		it.urmator();
	}
	it.current=-1;
	return it;
}

TElem Lista::modifica(const IteratorLP poz, const TElem e) const {
	if (!poz.valid()) {
		throw std::exception();
	}
	const TElem old=element[poz.current];
	element[poz.current]=e;
	return old;
}

void Lista::adauga(IteratorLP& poz, TElem e) {
	if (!poz.valid()) {
		throw std::exception();
	}
	if (firstFree==-1) {
		resize();
	}
	const int newNode=firstFree;
	firstFree=next[firstFree];
	element[newNode]=e;
	next[newNode]=next[poz.current];
	next[poz.current]=newNode;
	if (poz.current==tail) {
		tail=newNode;
	}
	poz.current=newNode;
	size++;
}

void Lista::adaugaInceput(TElem e) {
	if (firstFree==-1) {
		resize();
	}
	const int newNode=firstFree;
	firstFree=next[newNode];
	element[newNode]=e;
	next[newNode]=head;
	head=newNode;
	if (tail==-1) {
		tail=newNode;
	}
	size++;
}

void Lista::adaugaSfarsit(TElem e) {
	if (firstFree==-1) {
		resize();
	}
	int newNode=firstFree;
	firstFree=next[newNode];
	element[newNode]=e;
	next[newNode]=-1;
	if (tail!=-1) {
		next[tail]=newNode;
	}
	tail=newNode;
	if (head==-1) {
		head=newNode;
	}
	size++;
}

Lista::~Lista() {
	delete[] element;
	delete[] next;
}
void Lista::resize() {
	const int newCapacity=capacity*2;
	auto* newElement= new TElem[newCapacity];
	const auto newNext=new int[newCapacity];
	for (int i=0; i<capacity; i++) {
		newElement[i]=element[i];
		newNext[i]=next[i];
	}
	for (int i=capacity; i<newCapacity-1; i++) {
		newNext[i]=i+1;
	}
	newNext[newCapacity-1]=-1;
	delete[] element;
	delete[] next;
	element=newElement;
	next=newNext;
	firstFree=capacity;
	capacity=newCapacity;
}