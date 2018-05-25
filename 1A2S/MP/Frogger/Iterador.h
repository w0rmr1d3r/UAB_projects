#pragma once
#include "Node.h"
#include "Vehicle.h"
#include "Carril.h"

class Iterador
{
public:
	Iterador();
	Iterador(Node* posicio);
	~Iterador();

	void seguent();
	Vehicle& getElement() const;
	bool esNul() const;

private:
	Node* m_posicio;
};