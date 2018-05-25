#pragma once
#include "Vehicle.h"

class Node
{
public:
	Node();
	
	Vehicle& getValor();
	Node* getNext();
	void setValor(Vehicle valor);
	void setNext(Node* next);

	void mouVehicleDreta();
	void mouVehicleEsquerra();

private:
	Vehicle m_valor;
	Node* m_next;
};