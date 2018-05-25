#pragma once
#include "Area.h"
#include "lib\Grafic.h"
#include "Constants.h"

class ObjecteExtra
{
public:
	ObjecteExtra();
	ObjecteExtra(Grafic grafic);
	~ObjecteExtra();

	void setPosicioX();
	void setPosicioY();

	int getPosicioX();
	int getposicioY();

	int getVides();
	int getPunts();

	void setVisible();
	void setInivisible();

	bool getVisibilitat();

	bool obtencio(Area areaGranota);

	Area getAreaOcupada();
	void dibuixa();

	int randomNumber();

private:
	Grafic m_grafic;

	int m_vides;
	int m_punts;
	
	int m_posicioX;
	int m_posicioY;

	bool m_visible;
};