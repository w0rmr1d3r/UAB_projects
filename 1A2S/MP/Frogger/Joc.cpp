#include "Joc.h"

/**
 * Juga una partida
 * @param nivell nivell de la partida
 * @return
 */
int juga(int nivell)
{
	t_programStatus estat;

	// Inicialitzacions necessaries
	InitGame (estat);

	// Mostrar la finestra de joc
	Video_ShowWindow();

	// Creacio de variables d'estat del joc i de la pantalla
	Pantalla pantalla; // Objecte pantalla, des d'on controlarem tot el joc
	
	bool haMortLaGranota = false; // Variable boleana per saber si la granota esta morta
	bool granotaEntraCova = pantalla.esGranotaDinsCova(); // Variable per saber si la granota entra o no a la cova
	bool nivellSuperat = pantalla.nivellSuperat(); // Variable per saber si el nivell ha estat superat
	int vides = MAX_VIDES; // Vides que li queden a la granota
	int puntuacio = 0;

	pantalla.setVides(vides);
	pantalla.setNivell(nivell);
	pantalla.setPuntuacio(puntuacio);
 
	// Inizialitza llavor per a la funcio rand()
	//srand((unsigned) time(NULL));
	
	do
	{
		pantalla.inicia(nivell);

		do
		{
			pantalla.iniciaGranota();
			nivellSuperat = pantalla.nivellSuperat();
			haMortLaGranota = false;
			granotaEntraCova = pantalla.esGranotaDinsCova();
		
			do
			{
				pantalla.dibuixa();
				VideoUpdate(estat);
				ProcessEvents(estat);
				pantalla.sumaTemps();
				pantalla.restaTemps();
			
				if (Keyboard_GetKeyTrg(KEYBOARD_LEFT))
				{
					pantalla.mouGranota(ESQUERRA);
				}
				if (Keyboard_GetKeyTrg(KEYBOARD_RIGHT))
				{
					pantalla.mouGranota(DRETA);
				}
				if (Keyboard_GetKeyTrg(KEYBOARD_UP))
				{
					if ( !(pantalla.esGranotaDinsCova()) )
					{
						pantalla.mouGranota(AMUNT);
					}
					else
					{
						pantalla.resetTempsVides();
						pantalla.setPuntuacio(10);
					}
				}
				if (Keyboard_GetKeyTrg(KEYBOARD_DOWN))
				{
					pantalla.mouGranota(AVALL);
				}

				pantalla.mouVehicle();

				// si aquestes linies estan comentades, vol dir que s'esta evaluant el moviment de la granota
				// en cas de que no ho estiguin, el joc ha de funcionar amb els xocs
				if (pantalla.haMortLaGranota())
				{
					vides--;
					pantalla.setVides(-1);
					haMortLaGranota = true;
				}

				if (pantalla.chocObjecte())
				{
					vides = pantalla.getVides();
				}

				granotaEntraCova = pantalla.esGranotaDinsCova();
				
			} while ((!haMortLaGranota) && (!granotaEntraCova) && (!estat.bExit) && (!Keyboard_GetKeyTrg(KEYBOARD_ESCAPE)));

			nivellSuperat = pantalla.nivellSuperat();

		} while ((!nivellSuperat) && (vides > 0) && (!estat.bExit) && (!Keyboard_GetKeyTrg(KEYBOARD_ESCAPE)));

		if (nivellSuperat)
		{
			pantalla.setPuntuacio(10);
			nivell++;
			pantalla.setNivell(nivell);
		}

	} while ((nivell <= MAX_NIVELL) && (vides > 0) && (!estat.bExit) && (!Keyboard_GetKeyTrg(KEYBOARD_ESCAPE)));

	Video_Release();

	puntuacio = pantalla.getPuntuacio();
	return puntuacio;
}