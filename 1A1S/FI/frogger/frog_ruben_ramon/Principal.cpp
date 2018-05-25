// Fitxers de capcalera
#include <stdio.h>
#include <conio.h>
#include "Menu.h"
#include "Joc.h"
#include "GestioResultats.h"

// Declaracio de constants per a la seleccio dels menus
#define OPCIO_1 '1'
#define OPCIO_2 '2'
#define OPCIO_3 '3'
#define OPCIO_4 '4'
#define SORTIR '5'
#define SORTIR_2 '4'


void main()
{
	// Declaracio de variables a utulitzar
	char opcio;
	TipusJugador TMillorsJugadors[MAX_MILLORSJUGADORS];
	char nivell = '1';
	int Punts = 0;
	int posicio = -1;

	HANDLE hScreen;

	// Inicialitzem la taula de millors puntuacions cridant un procediment
	InicialitzarTaulaMillorsJugadors(TMillorsJugadors);

	// Bucle de creacio de menus per a escollir el tipus de joc
	do
	{
		printf("\n");
		MenuPrincipal();
		printf("\n");
		opcio = _getch();

		switch (opcio)
		{
		case OPCIO_1:
			Punts = Joc(nivell -'0');
			system("CLS");
			posicio = EsMillorPuntuacio(TMillorsJugadors, Punts);
			
			if (posicio >= 0)
			{
				DesplacarArray(TMillorsJugadors, posicio);
				EmplenarPosicioArray(TMillorsJugadors, Punts, posicio);
			}
			break;
		case OPCIO_2:
			do
			{
				MenuNivellDificultat();
				printf("\n");
				nivell = _getch();
				if ((nivell != '1') && (nivell != '2') && (nivell != '3'))
				{
					printf("Opcio incorrecta\n\n");
				}
				
			} while ((nivell != '1') && (nivell != '2') && (nivell != '3'));

			Punts = Joc(nivell - '0');
			system("CLS");
			posicio = EsMillorPuntuacio(TMillorsJugadors, Punts);

			if (posicio >= 0)
			{
				DesplacarArray(TMillorsJugadors, posicio);
				EmplenarPosicioArray(TMillorsJugadors, Punts, posicio);
			}
			break;
		case OPCIO_3:
			system("CLS");
			
			EscriuRanking(TMillorsJugadors);
			printf("\nPrem una tecla per tornar al menu principal\n");
			_getch();
			break;
		case OPCIO_4:
			do
			{
				MenuModes();
				printf("\n");
				opcio = _getch();

				switch (opcio)
				{
				case OPCIO_1:
					Joc2();
					break;
				case OPCIO_2:
					break;
				case OPCIO_3:
					break;
				case SORTIR:
					break;
				default:
					break;
				}
			} while (opcio != SORTIR_2);
			break;
		case SORTIR:
			break;
		default:
			break;
		}

	} while (opcio != SORTIR);

}