#include "Iterador.h"

Iterador::Iterador()
{
	m_posicio = NULL;
}

Iterador::Iterador(Node* posicio)
{
	m_posicio = posicio;
}

Iterador::~Iterador()
{
}

void Iterador::seguent()
{
	m_posicio = m_posicio->getNext();
}

Vehicle& Iterador::getElement() const
{
	return m_posicio->getValor();
}

bool Iterador::esNul() const
{
	return m_posicio == NULL;
}