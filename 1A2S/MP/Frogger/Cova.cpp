#include "Cova.h"

/**
 * Constructor per defecte.
*/
Cova::Cova()
{
}

/**
 * Constructor de la cova.
 * @param grafic Grafic ilustrant la cova
 * @param posicioX Coordenada horitzontal de la cova
 * @param posicioY Coordenada vertical de la cova
*/
Cova::Cova(Grafic grafic, int posicioX, int posicioY)
{
	// definim les seves coordenades, a mes a mes de les coordenades del dibuix de la granota un cop està la cova ocupada
	m_posicioX = posicioX;
	m_posicioY = posicioY;
	m_posicioOcupadaX = posicioX + BORDE_COVA_PETIT;
	m_posicioOcupadaY = posicioY + BORDE_COVA_GRAN;
	m_grafic = grafic;

	// definim les arees de la cova
	m_esquerra = Area(posicioX, posicioX + BORDE_COVA_PETIT, posicioY, posicioY + grafic.getScaleY());
	m_dreta = Area(posicioX + grafic.getScaleX() - BORDE_COVA_PETIT, posicioX + grafic.getScaleX(), posicioY, posicioY + grafic.getScaleY());
	m_interior = Area(posicioX + BORDE_COVA_PETIT, posicioX + grafic.getScaleX() - BORDE_COVA_PETIT, posicioY + BORDE_COVA_GRAN, posicioY + grafic.getScaleY());
	m_total = Area(posicioX, posicioX + grafic.getScaleX(), posicioY, m_posicioY + grafic.getScaleY());

	m_ocupada = false;
}

/**
 * Destructor per defecte.
*/
Cova::~Cova(void)
{
}

/**
 * Dibuixa la cova a la seva posició.
*/
void Cova::dibuixa()
{
	m_grafic.dibuixa(m_posicioX, m_posicioY);
}

/**
 * Comprova si la cova es accessible, tant per si esta ocupada com si solapa amb una area donada
 * @param area Area a comprovar
 * @returns false si no es accessible
*/
bool Cova::esAccessible(Area area)
{
	bool resultat = true;
	
	if ((m_ocupada) && (area.solapa(m_interior)))
	{
		resultat = false;	
	}
	
	return resultat;
}

/**
 * Comprova si l'area donada no es solapa amb les parets de la cova
 * @param area Area a comprovar
 * @returns false si es solapa amb alguna de les parets
*/
bool Cova::noSolapaParet(Area area)
{
	bool resultat = true;

	if ((m_dreta.solapa(area)) || (m_esquerra.solapa(area)))
	{
		resultat = false;
	}

	return resultat;
}

/**
 * Comprova si l'àrea donada es troba totalment a l'interior de la cova.
 * @param area Area a comprovar
 * @returns true si l'àrea es troba totalment dins la cova i false si no és així.
*/
bool Cova::esDins(Area area)
{
	bool resultat = false;

	if (m_interior.inclou(area))
	{
		resultat = true;
		m_ocupada = true;
	}

	return resultat;
}

/**
 * Indica si la cova esta o no ocupada
 * @returns m_ocupada true si la cova esta ocupada i false de lo contrari 
*/
bool Cova::getOcupada()
{
	return m_ocupada;
}

/**
 * Modifica l'estat d'ocupacio de la cova a true, es a dir, estara ocupada
*/
void Cova::setOcupada()
{
	m_ocupada = true;
}

/**
 * Modifica l'estat d'ocupacio de la cova a false contrari de setOcupada()
 * Es a dir, ara la cova es lliure i podra ser ocupada
*/
void Cova::setLliure()
{
	m_ocupada = false;
}

/**
 * Retorna la posicio X a on dibuixarem la granota quan la cova esta ocupada
 * @return posicioX a dibuixar la granota quan la cova esta ocupada
*/
int Cova::getPosicioOcupadaX()
{
	return m_posicioOcupadaX;
}

/**
* Retorna la posicio Y a on dibuixarem la granota quan la cova esta ocupada
* @return posicioY a dibuixar la granota quan la cova esta ocupada
*/
int Cova::getPosicioOcupadaY()
{
	return m_posicioOcupadaY;
}