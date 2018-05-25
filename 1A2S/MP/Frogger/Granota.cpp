#include "Granota.h"

/**
 * Constructor per defecte.
*/
Granota::Granota()
{
}

/**
 * Constructor de la Granota.
 * @param grafic Grafic amb el que ilustrar la granota
 * @param posicioInicialX Posició inicial en coordenada horitzontal
 * @param posicioInicialY Posició inicial en coordenada vertical
*/
Granota::Granota(Grafic grafic[MAX_GRAFIC_GRANOTA], int posicioInicialX, int posicioInicialY)
{
	int i = 0;
	for (i = 0; i < MAX_GRAFIC_GRANOTA; i++)
	{
		m_grafic[i] = grafic[i];
	}

	m_moviment = true;
	m_posicioActual = ADALT_2;

	m_posicioInicialX = posicioInicialX;
	m_posicioInicialY = posicioInicialY;
	m_posicioX = posicioInicialX;
	m_posicioY = posicioInicialY;
}

/**
 * Destructor per defecte.
*/
Granota::~Granota()
{
}

/**
 * Obté l'àrea ocupada per la granota.
 * @param opcio estat grafic actual de la granota
 * @return Area ocupada
*/
Area Granota::getAreaOcupada(OPCIONS_GRAFIC_GRANOTA opcio)
{
	Area area;

	area = Area(m_posicioX, m_posicioX + m_grafic[opcio].getScaleX(), m_posicioY, m_posicioY + m_grafic[opcio].getScaleY());

	return area;
}

/**
 * Dibuixa la granota a la posició actual.
 * @param opcio estat grafic actual de la granota
*/
void Granota::dibuixa(OPCIONS_GRAFIC_GRANOTA opcio)
{
	m_grafic[opcio].dibuixa(m_posicioX, m_posicioY);
}

/**
 * Mou la granota cap a l'esquerra.
*/
void Granota::mouEsquerra()
{
	setMoviment(false);

	if (getPosicio() == ESQUERRA_2)
	{
		m_posicioX -= 2*DESPLACAMENT_GRANOTA;
		setPosicio(ESQUERRA_1);
	}
	else
	{
		setPosicio(ESQUERRA_2);
		m_posicioX -= 2*DESPLACAMENT_GRANOTA;
	}
	
	setMoviment(true);
}

/**
 * Mou la granota cap a la dreta.
*/
void Granota::mouDreta()
{
	setMoviment(false);

	if (getPosicio() == DRETA_2)
	{
		m_posicioX += 2*DESPLACAMENT_GRANOTA;
		setPosicio(DRETA_1);
	}
	else
	{
		setPosicio(DRETA_2);
		m_posicioX += 2*DESPLACAMENT_GRANOTA;
	}
	
	setMoviment(true);
}

/**
 * Mou la granota cap amunt.
*/
void Granota::mouAmunt()
{
	setMoviment(false);

	if (getPosicio() == ADALT_2)
	{
		m_posicioY -= DESPLACAMENT_GRANOTA;
		setPosicio(ADALT_1);
	}
	else
	{
		setPosicio(ADALT_2);
		m_posicioY -= DESPLACAMENT_GRANOTA;
	}

	setMoviment(true);
}

/**
 * Mou la granota cap avall.
*/
void Granota::mouAvall()
{
	setMoviment(false);

	if (getPosicio() == ABAIX_2)
	{
		m_posicioY += 2*DESPLACAMENT_GRANOTA;
		setPosicio(ABAIX_1);
	}
	else
	{
		setPosicio(ABAIX_2);
		m_posicioY += 2 * DESPLACAMENT_GRANOTA;
	}

	setMoviment(true);
}

/**
 * Mou la granota a la seva posició inicial.
 * canvia la seva posicio grafica a ADALT_2
*/
void Granota::mouAPosicioInicial()
{
	m_posicioX = m_posicioInicialX;
	m_posicioY = m_posicioInicialY;
	m_posicioActual = ADALT_2;
}

/**
 * Retorna l'estat de moviment actual de la granota
 * true vol dir que es pot moure
 * false ho impideix
 * @return Moviment actual de la granota
*/
bool Granota::getMoviment()
{
	return m_moviment;
}

/**
 * Actualitza el moviment
 * @param moviment moviment true o false, depenent de si volem que es mogui o no la granota
*/
void Granota::setMoviment(bool moviment)
{
	m_moviment = moviment;
}

/**
 * Retorna la posicio grafica actual de la granota
 * @return Posicio grafica de la granota
*/
OPCIONS_GRAFIC_GRANOTA Granota::getPosicio()
{
	return m_posicioActual;
}

/**
 * Actualitza la posicio grafica de la granota
 * @param opcio posicio grafica de la granota que volem
*/
void Granota::setPosicio(OPCIONS_GRAFIC_GRANOTA opcio)
{
	m_posicioActual = opcio;
}