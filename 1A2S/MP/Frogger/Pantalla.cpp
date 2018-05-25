#include "Pantalla.h"

/**
 * Constructor de la Pantalla.
 * Aquesta pantalla té una sola cova imaginària ja que no té parets.
*/
Pantalla::Pantalla()
{
	// Carreguem els components grafics
	m_graficTemps[0].crea("data/numeros/numero0000.png");
	m_graficTemps[1].crea("data/numeros/numero0001.png");
	m_graficTemps[2].crea("data/numeros/numero0002.png");
	m_graficTemps[3].crea("data/numeros/numero0003.png");
	m_graficTemps[4].crea("data/numeros/numero0004.png");
	m_graficTemps[5].crea("data/numeros/numero0005.png");
	m_graficTemps[6].crea("data/numeros/numero0006.png");
	m_graficTemps[7].crea("data/numeros/numero0007.png");
	m_graficTemps[8].crea("data/numeros/numero0008.png");
	m_graficTemps[9].crea("data/numeros/numero0009.png");

	m_velocitat[0] = 3;
	m_velocitat[1] = 1;
	m_velocitat[2] = 2;
	m_velocitat[3] = 1;
	m_velocitat[4] = 2;

	m_graficVehicle[0].crea("data/GraficsGranota/Cotxe_3.png");
	m_graficVehicle[1].crea("data/GraficsGranota/Camio.png");
	m_graficVehicle[2].crea("data/GraficsGranota/Cotxe_2.png");
	m_graficVehicle[3].crea("data/GraficsGranota/Tractor.png");
	m_graficVehicle[4].crea("data/GraficsGranota/Cotxe_1.png");

	m_graficGranota[ADALT_1].crea("data/GraficsGranota/Granota_Amunt_1.png");
	m_graficGranota[ADALT_2].crea("data/GraficsGranota/Granota_Amunt_2.png");
	m_graficGranota[ABAIX_1].crea("data/GraficsGranota/Granota_Avall_1.png");
	m_graficGranota[ABAIX_2].crea("data/GraficsGranota/Granota_Avall_2.png");
	m_graficGranota[ESQUERRA_1].crea("data/GraficsGranota/Granota_Esquerra_1.png");
	m_graficGranota[ESQUERRA_2].crea("data/GraficsGranota/Granota_Esquerra_2.png");
	m_graficGranota[DRETA_1].crea("data/GraficsGranota/Granota_Dreta_1.png");
	m_graficGranota[DRETA_2].crea("data/GraficsGranota/Granota_Dreta_2.png");

	m_graficCova.crea("data/GraficsGranota/Cova120.png");
    m_graficFons.crea("data/GraficsGranota/Fons.png");

	m_graficVides.crea("data/GraficsGranota/Granota_Amunt_1.png");
	
	// Inicialitzem l'area total de la pantalla, així com l'espai pels carrils, el número de carrils i instanciem els objectes granota i cova.
	m_areaTotal = Area(INICI_X, FI_X, INICI_Y, FI_Y);
	m_iniciCarrilsY = INICI_Y + m_graficCova.getScaleY();
	
	m_granota = Granota(m_graficGranota, (FI_X - INICI_X - m_graficGranota[ADALT_1].getScaleX()) / 2, FI_Y - m_graficGranota[ADALT_1].getScaleY());

	int k = 0;
	for (k = 0; k < MAX_COVA; k++)
	{
		m_cova[k] = Cova(m_graficCova, INICI_X + k * m_graficCova.getScaleX(), INICI_Y);
	}

	int i = 0;
	for (i = 0; i < MAX_CARRIL; i++)
	{
		m_carril[i] = Carril(m_graficVehicle[i], m_iniciCarrilsY + i *((FI_Y - m_graficGranota[ADALT_1].getScaleY() - m_iniciCarrilsY) / MAX_CARRIL), m_velocitat[i], i+1);
		m_carril[i].afegeix();
	}

	int j = 0;
	for (j = 0; j < MAX_TEMPS_DIGIT; j++)
	{
		m_temps[j] = 0;
		m_tempsVides[j] = 9;
		m_puntuacio[j] = 0;
	}

	m_comptadorTemps = 0;
	m_restadorTemps = 0;
	m_vides = 0;

	m_ObjecteExtra = ObjecteExtra(m_graficGranota[ABAIX_2]);

	// Fixem l'hora actual com a llavor pel generador d'aleatoris.
	std::srand(std::time(0));
}

/**
 * Destructor per defecte
*/
Pantalla::~Pantalla()
{
	m_graficFons.destrueix();
	m_graficCova.destrueix();
	m_graficVides.destrueix();
	
	int j = 0;
	for (j = 0; j < MAX_GRAFIC_GRANOTA; j++)
	{
		m_graficGranota[j].destrueix();
	}

	int i = 0;
	for (i = 0; i < MAX_CARRIL; i++)
	{
		m_graficVehicle[i].destrueix();
		m_carril[i].~Carril();
	}

	int k = 0;
	for (k = 0; k < MAX_GRAFIC_TEMPS; k++)
	{
		m_graficTemps[k].destrueix();
	}
}

/**
 * Inicia la pantalla instanciant els vehicles i colocant la granota i els vehicles a la posició inicial.
 * Col·loca les coves lliures i actualitza la velocitat del vehicles
 * @param nivell Nivell de la pantalla.
*/
void Pantalla::inicia(int nivell)
{
	m_velocitat[0] = nivell*3;
	m_velocitat[1] = nivell;
	m_velocitat[2] = nivell * 2;
	m_velocitat[3] = nivell/2;
	m_velocitat[4] = nivell*2;

	// Eliminem tots els vehicles existents
	// Afegim un vehicle
	// movem aquest vehicle a la seva posicio inicial
	int i = 0;
	for (i = 0; i < MAX_CARRIL; i++)
	{
		m_carril[i].~Carril();
		m_carril[i].afegeix();
		m_carril[i].mouIniciCarril();
	}

	// iniciem totes les coves lliures, al iniciar el nivell
	int j = 0;
	for (j = 0; j < MAX_COVA; j++)
	{
		m_cova[j].setLliure();
	}

	m_ObjecteExtra.setPosicioX();
	m_ObjecteExtra.setPosicioY();
}

/**
 *  Mou la granota a la posicio inicial seva
*/
void Pantalla::iniciaGranota()
{
	m_granota.mouAPosicioInicial();
}

/**
 * Actualitza l'atribut de vides que te el mateix valor que les vides del joc
 * @param vides Vides actuals en el joc
*/
void Pantalla::setVides(int vides)
{
	m_vides += vides;
}

/**
 * Retorna les vides actuals del joc utilitzant la classe pantalla envers la variable de joc.cpp
 * @return vides Vides actuals en el joc
*/
int Pantalla::getVides()
{
	return m_vides;
}

/**
 * Retorna el nivell actual del joc
 * @return nivell del joc actual
*/
int Pantalla::getNivell()
{
	return m_nivell;
}

/**
 * Actualitza l'atribut de nivell dintre de la classe pantalla
 * @param nivell del joc
*/
void Pantalla::setNivell(int nivell)
{
	m_nivell = nivell;
}

/**
*  en construccio
*/
int Pantalla::getPuntuacio()
{
	int number = 0;

	number += m_puntuacio[0] + 10 * m_puntuacio[1] + 100 * m_puntuacio[2];

	return number;
}

/**
*  en construccio
*/
void Pantalla::setPuntuacio(int novaPuntuacio)
{
	int i = 0;
	if (m_puntuacio[0] == 9 && m_puntuacio[1] == 9 && m_puntuacio[2] == 9)
	{
	}
	else
	{
		if (novaPuntuacio < MAX_NUMBER_PUNTUACIO)
		{
			i = 1;
		}
		else
		{
			if (novaPuntuacio < MAX_NUMBER_PUNTUACIO_2)
			{
				i = 2;
			}
			else
			{
				i = 3;
			}
		}
	}
	switch (i)
	{
	case 1:
		if (m_puntuacio[2] != 9)
		{
			m_puntuacio[2]++;
		}
		else
		{
			m_puntuacio[2] = 0;
			if (m_puntuacio[1] != 9)
			{
				m_puntuacio[1]++;
			}
			else
			{
				m_puntuacio[1] = 0;
				if (m_puntuacio[0] != 9)
				{
					m_puntuacio[0]++;
				}
			}
		}
		break;
	case 2:
		if (m_puntuacio[1] != 9)
		{
			m_puntuacio[1]++;
		}
		else
		{
			m_puntuacio[1] = 0;
			if (m_puntuacio[0] != 9)
			{
				m_puntuacio[0]++;
			}
		}
		break;
	case 3:
		if (m_puntuacio[0] != 9)
		{
			m_puntuacio[0]++;
		}
		break;
	default: 
		break;
	}
	}


/**
 *  Suma el temps de la partida i el distribueix en 3 digits
*/
void Pantalla::sumaTemps()
{
	if (m_comptadorTemps < MAX_COMPTADOR_TEMPS)
	{
		m_comptadorTemps++;
	}
	else
	{
		m_comptadorTemps = 0;

		if (m_temps[0] == 9 && m_temps[1] == 9 && m_temps[2] == 9)
		{
			m_temps[0] = 0;
			m_temps[1] = 0;
			m_temps[2] = 0;
		}
		else
		{
			if (m_temps[0] == 9)
			{
				m_temps[0] = 0;

				if (m_temps[1] == 9)
				{
					m_temps[1] = 0;

					if (m_temps[2] == 9)
					{
					}
					else
					{
						m_temps[2]++;
					}
				}
				else
				{
					m_temps[1]++;
				}
			}
			else
			{
				m_temps[0]++;
			}
		}	
	}
}

void Pantalla::restaTemps()
{
	if (m_restadorTemps < MAX_RESTADOR_TEMPS)
	{
		m_restadorTemps += 1 * m_nivell;
	}
	else
	{
		m_restadorTemps = 0;

		if (m_tempsVides[0] == 0 && m_tempsVides[1] == 0 && m_tempsVides[2] == 0)
		{
			setVides(-1);
			m_tempsVides[0] = 9;
			m_tempsVides[1] = 9;
			m_tempsVides[2] = 9;
		}
		else
		{
			if (m_tempsVides[0] == 0)
			{
				m_tempsVides[0] = 9;

				if (m_tempsVides[1] == 0)
				{
					m_tempsVides[1] = 9;

					if (m_tempsVides[2] == 0)
					{
					}
					else
					{
						m_tempsVides[2]--;
					}
				}
				else
				{
					m_tempsVides[1]--;
				}
			}
			else
			{
				m_tempsVides[0]--;
			}
		}
	}
}

void Pantalla::resetTempsVides()
{
	int i = 0;
	for (i = 0; i < MAX_TEMPS_DIGIT; i++)
	{
		m_tempsVides[i] = 9;
	}
}

/**
 * Comprova si una àrea donada es troba dins la cova.
 * L'area donada sera sempre la posicio ADALT_1 de la granota
 * @return true si l'àrea es troba dins la cova i false si no s'hi troba. 
*/
bool Pantalla::esGranotaDinsCova()
{
	bool resultat = false;

	int i = 0;
	while (i < MAX_COVA && !resultat) 
	{
		resultat = m_cova[i].esDins(m_granota.getAreaOcupada(ADALT_1));
		i++;
	}

	if (resultat)
	{
		resetTempsVides();
		setPuntuacio(10);
	}
	return resultat;
}

/**
 * Comprova si una àrea donada es troba dins l'espai permés de moviment.
 * L'espai permes de moviment es el terreny de joc o area total
 * @param area Area a comprovar si es troba dins l'espai permés de moviment.
 * @return true si l'àrea es troba dins l'espai permés de moviment i false si no és així.
*/
bool Pantalla::espaiPermes(Area area)
{
	bool resultat = false;

	if (m_areaTotal.inclou(area))
	{
		resultat = true;
	}
	
	return resultat;
}

/**
 * Dibuixa tots els elements grafics a la posició on es troben.
*/
void Pantalla::dibuixa()
{
	m_graficFons.dibuixa(INICI_X, INICI_Y);

	int l = 0;
	for (l = 0; l < MAX_COVA; l++)
	{
		m_cova[l].dibuixa();
		if (m_cova[l].getOcupada())
		{
			m_graficGranota[ADALT_1].dibuixa(m_cova[l].getPosicioOcupadaX(), m_cova[l].getPosicioOcupadaY());
		}
	}

	m_granota.dibuixa(m_granota.getPosicio());

	int i = 0;
	for (i = 0; i < MAX_CARRIL; i++)
	{
		m_carril[i].dibuixa();
	}

	int j = 0;
	for (j = 0; j < getVides(); j++)
	{
		m_graficVides.dibuixa(INICI_X + j*m_graficVides.getScaleX(),FI_Y);
	}

	int k = 0;
	for (k = 0; k < MAX_TEMPS_DIGIT; k++)
	{
		m_graficTemps[m_temps[k]].dibuixa(FI_X - k * m_graficTemps[0].getScaleX() - m_graficTemps[0].getScaleX(), FI_Y);
		m_graficTemps[m_tempsVides[k]].dibuixa(FI_X - k * m_graficTemps[0].getScaleX() - m_graficTemps[0].getScaleX(), FI_Y + m_graficTemps[0].getScaleY());
		m_graficTemps[m_puntuacio[k]].dibuixa(INICI_X + k * m_graficTemps[0].getScaleX(), FI_Y + m_graficVides.getScaleY());
	}

	if (m_ObjecteExtra.getVisibilitat())
	{
		m_ObjecteExtra.dibuixa();
	}
}

/**
 * Mou els vehicles dintre dels carrils.
 * Afegeix de nous aleatoriament
 * Si es treuen tots els cotxes i no n'hi ha mes a la cua, n'afegeix per a que no quedi el carril buit
*/
void Pantalla::mouVehicle()
{
	int i = 0;
	for (i = 0; i < MAX_CARRIL; i++)
	{
		if ( espaiPermes(m_carril[i].getAreaOc()) )
		{
			m_carril[i].mouVehicle();

			if (m_carril[i].afegirCotxeRand(getNivell()))
			{
				m_carril[i].afegeix();
			}
		}
		else
		{
			m_carril[i].treu();

			if (m_carril[i].esBuida())
			{
				m_carril[i].afegeix();
			}
		}

		
	}
}

/**
 * Comprova si la granota ha mort.
 * @return true si la granota és morta i false si és viva.
*/
bool Pantalla::haMortLaGranota()
{
	bool mort = false;
	
	int i = 0;
	while (i < MAX_CARRIL && !mort)
	{
		if ( m_carril[i].chocGranota(m_granota.getAreaOcupada(m_granota.getPosicio())) )
		{
			mort = true;
		}
		else
		{
			i++;
		}
	}
	
	return mort;
}

/**
 * Mou la granota en la direcció donada.
 * Comproba em el cas de les coves, que siguin accessibles
 * @param direccio Direcció cap a on s'ha de moure la granota.
*/
void Pantalla::mouGranota(int direccio)
{
	bool esAccesible = true;
	bool solapaParet = true;

	if (espaiPermes(m_granota.getAreaOcupada(ADALT_1)))
	{
		switch (direccio)
		{
		case AMUNT:
			if (m_granota.getAreaOcupada(ADALT_1).getMinY() > m_areaTotal.getMinY())
			{
				if (m_granota.getMoviment())
				{
					int i = 0;
					while (i < MAX_COVA && solapaParet && esAccesible)
					{
						if (!m_cova[i].esAccessible(m_granota.getAreaOcupada(m_granota.getPosicio())))
						{
							esAccesible = false;
						}
						if (!m_cova[i].noSolapaParet(m_granota.getAreaOcupada(m_granota.getPosicio())))
						{
							solapaParet = false;
						}
						i++;
					}
					if (esAccesible && solapaParet)
					{
						m_granota.mouAmunt();
					}
				}
			}
			break;
		case AVALL:
			if (m_granota.getAreaOcupada(ABAIX_1).getMaxY() < m_areaTotal.getMaxY())
			{
				if (m_granota.getMoviment())
				{
					m_granota.mouAvall();
				}
			}
			break;
		case DRETA:
			if (m_granota.getAreaOcupada(DRETA_1).getMaxX() < m_areaTotal.getMaxX())
			{
				if (m_granota.getMoviment())
				{
					m_granota.mouDreta();
				}
			}
			break;
		case ESQUERRA:
			if (m_granota.getAreaOcupada(ESQUERRA_1).getMinX() > m_areaTotal.getMinX())
			{
				if (m_granota.getMoviment())
				{
					m_granota.mouEsquerra();
				}
			}
			break;
		default:
			break;
		}
	}
}

/**
 * Funcio que ens indica si el nivell esta superat comprobant l'ocupacio de totes les coves
 * @return true si el nivell esta superat, false de lo contrari
*/
bool Pantalla::nivellSuperat()
{
	bool resultat = true;

	int i = 0;
	while (i < MAX_COVA && resultat)
	{
		if (m_cova[i].getOcupada())
		{
			i++;
		}
		else
		{
			resultat = false;
		}
	}

	return resultat;
}


bool Pantalla::chocObjecte()
{
	bool obtencio = false;

	if (m_ObjecteExtra.obtencio(m_granota.getAreaOcupada(m_granota.getPosicio())))
	{
		obtencio = true;

		setPuntuacio(m_ObjecteExtra.getPunts());
		setVides(m_ObjecteExtra.getVides());

		m_ObjecteExtra.setInivisible();
	}

	return obtencio;
}