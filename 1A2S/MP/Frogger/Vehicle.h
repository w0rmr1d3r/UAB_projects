#pragma once
#include "Area.h"
#include "lib\Grafic.h"

// nº de píxels que es desplaça el cotxe a cada moviment del nivell 1
const int DESPLACAMENT_COTXE = 1;

class Vehicle
{
public:
	Vehicle();
	Vehicle(Grafic grafic, int velocitat);
	Vehicle(Vehicle &obj);
	~Vehicle();

	Area getAreaOcupada();

	int getScaleX();
	int getScaleY();

	void setPosicioX(int posicioX);
	void setPosicioY(int posicioY);

	int getPosicioX();
	int getPosicioY();

	void dibuixa();

	void mouDreta();
	void mouEsquerra();
	void mouAIniciCarril(int posicioX, int posicioY);

private:
	Grafic m_grafic;

	int m_posicioX;
	int m_posicioY;
	int m_velocitat;
};

