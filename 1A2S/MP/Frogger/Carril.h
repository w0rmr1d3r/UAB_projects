#pragma once
#include "Area.h"
#include "lib\Grafic.h"
#include "Vehicle.h"
#include "Iterador.h"
#include "Node.h"
#include <stdlib.h>
#include "Constants.h"
#include <cstdlib>
#include <ctime>
#include <stdlib.h>
#include <time.h>

class Carril
{
	public:
		Carril();
		Carril(Grafic grafic, int nCarril, int velocitat, int sentit);
		~Carril();

		void mouIniciCarril();
		void dibuixa();
		Area getAreaOc();
		void mouVehicle();

		void afegeix();
		void treu();

		Vehicle& getPrimer() const;
		Vehicle& getUltim() const;
		Node* getInici();
		Node* getUltim();

		bool esBuida() const;
		
		bool afegirCotxeRand(int nivell);
		bool chocGranota(Area areaGranota);

	private:
		Vehicle m_vehicle;
		int m_nCarril;
		int m_sentit;

		Node* m_primer;
		Node* m_ultim;
};