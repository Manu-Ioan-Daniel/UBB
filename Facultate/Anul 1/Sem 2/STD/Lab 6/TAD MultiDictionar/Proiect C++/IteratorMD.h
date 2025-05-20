#pragma once
#include "MD.h"

class IteratorMD {
	friend class MD;

private:
	const MD& md;
	int pozCurenta;
	int valIndex; // index în vectorul de valori
	IteratorMD(const MD& c);
	void avanseaza();

public:
	void prim();
	void urmator();
	bool valid() const;
	TElem element() const;
};
