#include "ObjecteExtra.h"

ObjecteExtra::ObjecteExtra()
{
	m_vides = 1;
	m_punts = 100;
	m_visible = true;
}

ObjecteExtra::ObjecteExtra(Grafic grafic)
{
	m_grafic = grafic;
	m_vides = 1;
	m_punts = 100;
	m_visible = true;
}

ObjecteExtra::~ObjecteExtra()
{
}

void ObjecteExtra::setPosicioX()
{
	int number = randomNumber();
	int novaPosicio = INICI_X + number*INICI_X;

	if (novaPosicio + m_grafic.getScaleX() < FI_X)
	{
		m_posicioX = novaPosicio;
	}
	else
	{
		m_posicioX = FI_X / 2;
	}
}

void ObjecteExtra::setPosicioY()
{
	int number = randomNumber();
	int novaPosicio = INICI_Y + number*INICI_Y;

	if (novaPosicio + m_grafic.getScaleX() < FI_Y)
	{
		m_posicioX = novaPosicio;
	}
	else
	{
		m_posicioX = FI_Y / 2;
	}
}

int ObjecteExtra::getPosicioX()
{
	return m_posicioX;
}

int ObjecteExtra::getposicioY()
{
	return m_posicioY;
}

int ObjecteExtra::getVides()
{
	return m_vides;
}

int ObjecteExtra::getPunts()
{
	return m_punts;
}

void ObjecteExtra::setVisible()
{
	m_visible = true;
}

bool ObjecteExtra::getVisibilitat()
{
	return m_visible;
}

void ObjecteExtra::setInivisible()
{
	m_visible = false;
}

bool ObjecteExtra::obtencio(Area areaGranota)
{
	bool resultat = false;

	if (getAreaOcupada().solapa(areaGranota))
	{
		resultat = true;
	}

	return resultat;
}

Area ObjecteExtra::getAreaOcupada()
{
	Area area;

	area = Area(m_posicioX, m_posicioX + m_grafic.getScaleX(), m_posicioY, m_posicioY + m_grafic.getScaleY());

	return area;
}

void ObjecteExtra::dibuixa()
{
	m_grafic.dibuixa(m_posicioX, m_posicioY);
}

int ObjecteExtra::randomNumber()
{
	int number;
	std::srand(std::time(0));

	number = rand() % (MAX_RAND_NUMBER - MIN_RAND_NUMBER + 1) + MIN_RAND_NUMBER;

	return number;
}