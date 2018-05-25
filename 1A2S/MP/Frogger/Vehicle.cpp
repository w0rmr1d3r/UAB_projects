#include "Vehicle.h"
#include "Pantalla.h"

/**
 * Constructor per defecte.
*/
Vehicle::Vehicle()
{
}

/**
 * Constructor del Vehicle.
 * @param grafic Grafic amb el que ilustrar el vehicle
 * @param velocitat Velocitat de moviment
*/
Vehicle::Vehicle(Grafic grafic, int velocitat)
{
	m_grafic = grafic;
	m_velocitat = velocitat;
}

/**
 * Constructor de copia
 * @param obj objecte a copiar
*/
Vehicle::Vehicle(Vehicle &obj)
{
	m_grafic = obj.m_grafic;
	m_posicioX = obj.m_posicioX;
	m_posicioY = obj.m_posicioY;
	m_velocitat = obj.m_velocitat;
}

/**
 * Destructor per defecte.
*/
Vehicle::~Vehicle()
{
}

/**
 * Actualitzacio de la posicioX del vehicle
 * @param posicioX posicio actualitzada
*/
void Vehicle::setPosicioX(int posicioX)
{
	m_posicioX = posicioX;
}

/**
 * Actualitzacio de la posicioY del vehicle
 * @param posicioY posicio actualitzada
*/
void Vehicle::setPosicioY(int posicioY)
{
	m_posicioY = posicioY;
}

int Vehicle::getPosicioY()
{
	return m_posicioY;
}

int Vehicle::getPosicioX()
{
	return m_posicioX;
}


/**
 * Retorna l'area ocupada pel vehicle.
 * @return Area ocupada pel vehicle
*/
Area Vehicle::getAreaOcupada()
{
	Area area;

	area = Area(m_posicioX, m_posicioX + m_grafic.getScaleX(), m_posicioY, m_posicioY + m_grafic.getScaleY());
	
	return area;
}

/**
 * Dibuixa el vehicle a la posició actual.
*/
void Vehicle::dibuixa()
{
	m_grafic.dibuixa(m_posicioX, m_posicioY);
}

/**
 * Mou el vehicle cap a la dreta
 * En altres paraules, modifica la seva posicio X
*/
void Vehicle::mouDreta()
{
	m_posicioX += DESPLACAMENT_COTXE + (2 * m_velocitat);
}

/**
 * Mou el vehicle cap a l'esquerra
 * En altres paraules, modifica la seva posicio X
*/
void Vehicle::mouEsquerra()
{
	m_posicioX -= DESPLACAMENT_COTXE + (2 * m_velocitat);
}

/**
 * Mou el vehicle a l'inici d'un carril.
 * @param iniciXCarril Coordenada X inicial del carril
 * @param iniciYCarril Coordenada Y del carril
*/
void Vehicle::mouAIniciCarril(int iniciXCarril, int iniciYCarril)
{
	m_posicioX = iniciXCarril;
	m_posicioY = iniciYCarril;
}

/**
 * Retorna el tamany X del grafic del vehicle
 * @return X grafic Scale del vehicle
*/
int Vehicle::getScaleX()
{
	return m_grafic.getScaleX();
}

/**
 * Retorna el tamany Y del grafic del vehicle
 * @return Y grafic Scale del vehicle
*/
int Vehicle::getScaleY()
{
	return m_grafic.getScaleY();
}