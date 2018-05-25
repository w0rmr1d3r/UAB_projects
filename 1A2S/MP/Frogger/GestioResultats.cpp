#include "GestioResultats.h"

void iniciaTaulaMillorJugadors(TipusJugador TMillorsJugadors[])
{
	ifstream fitxerRanking;

	fitxerRanking.open(nomFitxer);
	if (fitxerRanking.is_open())
	{
		char nomJugador[MAX_PLAYER_NAME];
		int puntuacioJugador;

		fitxerRanking >> nomJugador >> puntuacioJugador;

		int i = 0;
		while (!fitxerRanking.eof() && i < MAX_PLAYER_RANKING)
		{
			strcpy_s(TMillorsJugadors[i].nom, nomJugador);
			TMillorsJugadors[i].puntuacio = puntuacioJugador;

			fitxerRanking >> nomJugador >> puntuacioJugador;
			i++;
		}
		fitxerRanking.close();
	}
}

int haMilloratPuntuacio(TipusJugador TMillorsJugadors[], int punts)
{
	int i = 0;
	int trobat = 0;

	do
	{
		if (TMillorsJugadors[i].puntuacio < punts)
		{
			trobat = 1;
		}
		i++;
	} while ((i < MAX_PLAYER_RANKING) && (!trobat));

	if (trobat)
	{
		return (i - 1);
	}
	else
	{
		return -1;
	}
}

void desplacaArray(TipusJugador TMillorsJugadors[], int posicio)
{
	int i;
	for (i = MAX_PLAYER_RANKING - 1; i > posicio; i--)
	{
		strcpy_s(TMillorsJugadors[i].nom, TMillorsJugadors[i - 1].nom);
		TMillorsJugadors[i].puntuacio = TMillorsJugadors[i - 1].puntuacio;
	}
}

void emplenaPosicioArray(TipusJugador &TMillorsJugadors, int punts)
{
	system("cls");
	printf("Entra el teu nom\n");
	scanf_s("%s", &(TMillorsJugadors.nom));
	(TMillorsJugadors).puntuacio = punts;
}

void mostraRanking()
{
	ifstream fitxerRanking;

	fitxerRanking.open(nomFitxer);
	if (fitxerRanking.is_open())
	{
		char nomJugador[MAX_PLAYER_NAME];
		int puntuacioJugador;

		fitxerRanking >> nomJugador >> puntuacioJugador;

		int i = 0;
		while (!fitxerRanking.eof() && i < MAX_PLAYER_RANKING)
		{
			cout << i + 1 << "- " << nomJugador << " " << puntuacioJugador << endl;
			i++;
			fitxerRanking >> nomJugador >> puntuacioJugador;
		}
		fitxerRanking.close();
	}
}


void escriuRankingFitxer(TipusJugador TMillorsJugadors[MAX_PLAYER_RANKING])
{
	ofstream fitxerRanking; 
 
	fitxerRanking.open(nomFitxer); 
	if (fitxerRanking.is_open()) 
	{ 
		for (int i = 0; i < MAX_PLAYER_RANKING; i++)
		{
			fitxerRanking << TMillorsJugadors[i].nom << " " << TMillorsJugadors[i].puntuacio << "\n"; 
		}
		fitxerRanking.close();
	}
}
