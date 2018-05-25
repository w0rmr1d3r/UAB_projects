#include "Carril.h"

/**
 *	Constructor per defecte
*/
Carril::Carril()
{
}

/**
 *	Constructor amb sobrecarrega
 *	@param grafic grafic del vehicle al carril corresponent
 *	@param nCarril numero del carril al qual pertany
 *	@param velocitat velocitat del vehicle
 *  @param sentit sentit del vehicle dintre de la pantalla
*/
Carril::Carril(Grafic grafic, int nCarril, int velocitat, int sentit)
{
	m_nCarril = nCarril;
	m_sentit = sentit;
	m_vehicle = Vehicle(grafic, velocitat);

	if (m_sentit % 2 == 0)
	{
		m_vehicle.setPosicioX(FI_X - m_vehicle.getScaleX());
		m_vehicle.setPosicioY(nCarril);
	}
	else
	{
		m_vehicle.setPosicioX(INICI_X);
		m_vehicle.setPosicioY(nCarril);
	}

	m_primer = NULL;
	m_ultim = NULL;
}

/**
 *	Destructor per defecte
 *  Destrueix la cua de vehicles
*/
Carril::~Carril()
{
	while (!esBuida())
	{
		treu();
	}
}

/**
 *	Mou el vehicle a l'inici del carril
 *  Depent del seu sentit l'iniciara a l'esquerra o dreta de la pantalla
 *  Ja no s'utilitza, sino que s'afegeixen i treuen els vehicles de la cua
 *  Tot i això, está implementada per a que funcioni a la cua
*/
void Carril::mouIniciCarril()
{
	Iterador posicio;
	posicio = Iterador(getInici());

	while (!posicio.esNul())
	{
		Vehicle& vehicle = posicio.getElement();
		if (m_sentit % 2 == 0)
		{
			m_vehicle.mouAIniciCarril(FI_X - m_vehicle.getScaleX(), m_nCarril);
		}
		else
		{
			m_vehicle.mouAIniciCarril(INICI_X, m_nCarril);
		}
		posicio.seguent();
	}
	
}

/**
 *	Dibuixa els vehicles de la cua
*/
void Carril::dibuixa()
{	
	Iterador posicio;
	posicio = Iterador(getInici());

	while (!posicio.esNul())
	{
		Vehicle& vehicle = posicio.getElement();
		vehicle.dibuixa();
		posicio.seguent();
	}

}

/**
 *	Retorna l'area ocupada pel vehicle en el carril
 *	@return Area ocupada pel vehicle
*/
Area Carril::getAreaOc()
{
	return m_primer->getValor().getAreaOcupada();
	//return m_vehicle.getAreaOcupada();
}

/**
 *	Mou els vehicles dintre del carril i dins de la cua depenent del seu sentit
*/
void Carril::mouVehicle()
{
	Iterador posicio;
	posicio = Iterador(getInici());

	while (!posicio.esNul())
	{
		Vehicle& vehicle = posicio.getElement();

		if (m_sentit % 2 == 0)
		{	
			vehicle.mouEsquerra();
		}
		else
		{
			vehicle.mouDreta();
		}

		posicio.seguent();
	}
}

/**
 *	Afegeix un vehicle a la cua
 *  es a dir, afegeix un nou node dins de la cua
*/
void Carril::afegeix()
{
	Node* aux;

	if ((aux = new Node) != NULL)
	{
		aux->setValor(m_vehicle);
		aux->setNext(NULL);

		if (esBuida())
		{
			m_primer = aux;
		}
		else
		{
			m_ultim->setNext(aux);
		}
			
		m_ultim = aux;
	}
}

/**
 *	Treu un vehicle de la cua
*/
void Carril::treu()
{
	Node* aux;

	if (!esBuida())
	{
		aux = m_primer;

		if (m_primer == m_ultim)
		{
			m_ultim = NULL;
		}

		m_primer = aux->getNext();
		delete aux;
	}
}

/**
 *	Retorna el primer vehicle de la cua
 *  @return vehicle el primer vehicle de la cua
*/
Vehicle& Carril::getPrimer() const
{
	return m_primer->getValor();
}

/**
 *	Retorna l'ultim vehicle de la cua
 *  @return vehicle l'ultim de la cua
*/
Vehicle& Carril::getUltim() const
{
	return m_ultim->getValor();
}

/**
 *	Indica si la cua de vehicles es buida o no
 *  @return true si la cua es buida, false de lo contrari
*/
bool Carril::esBuida() const
{
	return m_primer == NULL;
}

/**
 *	Retorna el primer node de la cua de vehicles
 *  @return m_primer el primer node dins de la cua
*/
Node* Carril::getInici()
{
	return m_primer;
}

/**
 *	Retorna l'ultim node de la cua de vehicles
 *   @return m_ultim l'ultim node dins de la cua
*/
Node* Carril::getUltim()
{
	return m_ultim;
}

/**
 *	Indica si et pot afegir un vehicle a la cua, depenent del nivell canvien les probabilitats
 *  A mes nivell, mes probabilitat
 *  @param nivell depenent del nivell, sera possible afegir amb mes probabilitats
 *  @return true si s'afegeix un vehicle, fals de lo contrari
*/
bool Carril::afegirCotxeRand(int nivell)
{
	bool resultat = false;
	int number;
	Vehicle ultimVehicle = m_ultim->getValor();

	std::srand(std::time(0));

	number = rand() % (MAX_RAND_NUMBER - MIN_RAND_NUMBER + 1) + MIN_RAND_NUMBER;

	if (m_sentit % 2 == 0)
	{
		if (ultimVehicle.getPosicioX() + ultimVehicle.getScaleX() < FI_X - ultimVehicle.getScaleX())
		{
			if (nivell >= number)
			{
				resultat = true;
			}
		}
	}
	else
	{
		if (ultimVehicle.getPosicioX() > INICI_X + ultimVehicle.getScaleX())
		{
			if (nivell >= number)
			{
				resultat = true;
			}
		}
	}

	return resultat;
}

/**
 *	Indica si hi ha hagut xoc amb la granota
 *  ho comproba per a tots els vehicles de la cua
 *  @param areaGranota area de la granota a comprobar (sempre en la seva posicio actual)
 *  @return true hi ha xoc, false de lo contrari
*/
bool Carril::chocGranota(Area areaGranota)
{
	bool resultat = false;
	Iterador posicio;
	posicio = Iterador(getInici());

	while (!posicio.esNul())
	{
		Vehicle& vehicle = posicio.getElement();
		
		if (vehicle.getAreaOcupada().solapa(areaGranota))
		{
			resultat = true;
		}
		posicio.seguent();
	}
	return resultat;
}