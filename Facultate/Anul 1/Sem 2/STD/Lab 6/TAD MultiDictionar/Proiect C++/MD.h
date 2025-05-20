#pragma once
#include <vector>
#include <utility>
using namespace std;
typedef int TCheie;
typedef int TValoare;
typedef std::pair<TCheie, TValoare> TElem;
#define NULL_TCHEIE -999999
#define INITIAL_CAPACITY 13
struct Entry {
	TCheie key;
	vector<TValoare> values;
	bool occupied;
	bool deleted;
};

class IteratorMD;

class MD {
	friend class IteratorMD;

private:
	Entry* tabela;
	int capacitate;
	int nrElemente;

	int hashPrim(TCheie c) const;
	int hashSecund(TCheie c) const;
	int dubluHashing(TCheie c, int i) const;
	void redimensionare();

public:
	MD();
	void adauga(TCheie c, TValoare v);
	vector<TValoare> cauta(TCheie c) const;
	bool sterge(TCheie c, TValoare v);
	int dim() const;
	bool vid() const;
	IteratorMD iterator() const;
	~MD();
};
