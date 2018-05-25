#include "Menu.h"
#include "Joc.h"
#include "GestioResultats.h"
#include <iostream>

using namespace std;

const char OPCIO_JUGAR = '1';
const char OPCIO_CONFIGURAR = '2';
const char OPCIO_PUNTUACIO = '3';
const char OPCIO_SORTIR = '4';

/**
 * Programa principal que permet jugar al joc de la granota (frog) desant puntuacions
 * @return
 */
int main()
{
	char opcio;
	int punts = 0;;
	int posicio = -1;
	int nivell = 1;
	TipusJugador millorsJugadors[MAX_PLAYER_RANKING];

	iniciaTaulaMillorJugadors(millorsJugadors);

	do
	{
		mostraMenuPrincipal();
		opcio = _getch();
		switch (opcio)
		{
			case OPCIO_JUGAR:
				punts = juga(nivell);
				
				posicio = haMilloratPuntuacio(millorsJugadors, punts);

				if (posicio >= 0)
				{
					desplacaArray(millorsJugadors, posicio);
					emplenaPosicioArray(millorsJugadors[posicio], punts);
					escriuRankingFitxer(millorsJugadors);
				}

				break;
			case OPCIO_CONFIGURAR:
				do
				{
					mostraMenuNivellDificultat();
					nivell = _getch();
					if ((nivell != '1') && (nivell != '2') && (nivell != '3'))
					{
						cout << "Opcio incorrecta.\n";
					}
				} while ((nivell != '1') && (nivell != '2') && (nivell != '3'));
				break;
			case OPCIO_PUNTUACIO:
				mostraRanking();
				cout << "Prem una tecla per tornar al menu principal";
				_getch();
				break;
			default:
				cout << "tecla no permitida.\n";
				break;
		}
	} while (opcio != OPCIO_SORTIR);
	
	return 0;
}
