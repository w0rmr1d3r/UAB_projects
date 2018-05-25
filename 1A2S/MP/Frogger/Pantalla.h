#pragma once
#include "Area.h"
#include "lib\Grafic.h"
#include "Cova.h"
#include "Vehicle.h"
#include "Granota.h"
#include "Carril.h"
#include "Constants.h"
#include "ObjecteExtra.h"

const int AMUNT = 0;
const int AVALL = 1;
const int DRETA = 2;
const int ESQUERRA = 3;

// Numero de carrils maxims
const int MAX_CARRIL = 5;

// Numero de coves maximes al joc
const int MAX_COVA = 5;

// Maxims de temps
const int MAX_TEMPS_DIGIT = 3;
const int MAX_COMPTADOR_TEMPS = 5;
const int MAX_RESTADOR_TEMPS = 1;
const int MAX_GRAFIC_TEMPS = 10;

// Constants sobre la puntuacio
const int MAX_PUNTUACIO = 3;
const int MAX_NUMBER_PUNTUACIO = 9;
const int MAX_NUMBER_PUNTUACIO_2 = 99;
const int MAX_NUMBER_TOTAL = 999;

class Pantalla
{
public:
	Pantalla();
	~Pantalla();

	void inicia(int nivell);
	void iniciaGranota();

	void dibuixa();

	void mouGranota(int direccio);
	void mouVehicle();

	bool esGranotaDinsCova();
	bool haMortLaGranota();

	void setVides(int vides);
	int getVides();

	void sumaTemps();
	void restaTemps();
	void resetTempsVides();

	bool nivellSuperat();
	int getNivell();
	void setNivell(int nivell);

	int getPuntuacio();
	void setPuntuacio(int nivell);

	bool chocObjecte();

private:
	Grafic m_graficFons;
	Grafic m_graficCova;
	Grafic m_graficGranota[MAX_GRAFIC_GRANOTA];
	Grafic m_graficVehicle[MAX_CARRIL];

	Area m_areaTotal;
	Cova m_cova[MAX_COVA];
	Carril m_carril[MAX_CARRIL];
	Granota m_granota;
	
	int m_iniciCarrilsY;
	int m_velocitat[MAX_CARRIL];
	bool espaiPermes(Area area);

	int m_vides;
	Grafic m_graficVides;

	int m_temps[MAX_TEMPS_DIGIT];
	int m_tempsVides[MAX_TEMPS_DIGIT];
	int m_comptadorTemps;
	int m_restadorTemps;
	Grafic m_graficTemps[MAX_GRAFIC_TEMPS];

	int m_nivell;

	ObjecteExtra m_ObjecteExtra;

	//iniciar a 0 en el constructor
	int m_puntuacio[MAX_PUNTUACIO];
};

