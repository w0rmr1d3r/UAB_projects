#pragma once
#include "Area.h"
#include "lib\Grafic.h"

// nº de píxels que es desplaça la granota a cada moviment
const int DESPLACAMENT_GRANOTA = 5;
// quantitat de grafics de la granota, estats grafics a on pot estar, corresponen a les imatges
const int MAX_GRAFIC_GRANOTA = 8;
typedef enum{ADALT_1, ADALT_2, ABAIX_1, ABAIX_2, ESQUERRA_1, ESQUERRA_2, DRETA_1, DRETA_2}OPCIONS_GRAFIC_GRANOTA;

class Granota
{
public:
	Granota();
	Granota(Grafic grafic[MAX_GRAFIC_GRANOTA], int posicioInicialX, int posicioInicialY);
	~Granota();

	Area getAreaOcupada(OPCIONS_GRAFIC_GRANOTA opcio);
	void dibuixa(OPCIONS_GRAFIC_GRANOTA opcio);

	void mouAmunt();
	void mouAvall();
	void mouDreta();
	void mouEsquerra();
	void mouAPosicioInicial();

	bool getMoviment();
	void setMoviment(bool moviment);

	OPCIONS_GRAFIC_GRANOTA getPosicio();
	void setPosicio(OPCIONS_GRAFIC_GRANOTA opcio);

private:

	Grafic m_grafic[MAX_GRAFIC_GRANOTA];

	bool m_moviment;
	OPCIONS_GRAFIC_GRANOTA m_posicioActual;

	int m_posicioX;
	int m_posicioY;
	int m_posicioInicialX;
	int m_posicioInicialY;
};

