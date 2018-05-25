#include "Node.h"

Node::Node()
{
	Vehicle m_valor;
	Node* m_next = NULL;
}

Vehicle& Node::getValor()
{
	return m_valor;
}

Node* Node::getNext()
{
	return m_next;
}

void Node::setValor(Vehicle valor)
{
	//m_valor = Vehicle(valor);
	m_valor = valor;
}

void Node::setNext(Node* next)
{
	m_next = next;
}

void Node::mouVehicleDreta()
{
	m_valor.mouDreta();
}

void Node::mouVehicleEsquerra()
{
	m_valor.mouEsquerra();
}
