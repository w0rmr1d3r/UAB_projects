#pragma once
#include "Area.h"
#include "lib\Grafic.h"

// Constants utilitzades per a definir les 3 arees de la cova
const int BORDE_COVA_GRAN = 20;
const int BORDE_COVA_PETIT = 10;

class Cova
{
public:
	Cova();
	Cova(Grafic grafic, int posicioX, int posicioY);
	~Cova();

	void dibuixa();
	bool esAccessible(Area area);
	bool esDins(Area area);
	bool noSolapaParet(Area area);

	bool getOcupada();
	void setOcupada();
	void setLliure();

	int getPosicioOcupadaX();
	int getPosicioOcupadaY();

private:
	int m_posicioX;
	int m_posicioY;

	int m_posicioOcupadaX;
	int m_posicioOcupadaY;

	Grafic m_grafic;
	Area m_total;
	Area m_interior;
	Area m_esquerra;
	Area m_dreta;

	bool m_ocupada;
};

