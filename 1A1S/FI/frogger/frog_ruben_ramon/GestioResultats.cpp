#include "GestioResultats.h"


// Procediment que crea la taula de millors puntuacions
void InicialitzarTaulaMillorsJugadors(TipusJugador TMillorsJugadors[])
{
	int i, j;
	int k = MAX_CARACTERS - 2;

	for (i = 0; i < MAX_MILLORSJUGADORS; i++)
	{
		for (j = 0; j < k; j++)
		{
			TMillorsJugadors[i].Nom[j] = 'A';
		}

		TMillorsJugadors[i].Nom[k+1] = '\0';
		TMillorsJugadors[i].puntuacio = 0;
	}
}

// Funcio que retorna la posicio de la puntuacio que ha fet depepent si esta entre les maximes puntuacions
int EsMillorPuntuacio(TipusJugador TMillorsJugadors[], int Punts)
{
	int i = 0;
	int trobat = 0;

	do
	{
		if (TMillorsJugadors[i].puntuacio < Punts)
		{
			trobat = 1;
		}
		i++;

	} while ((i < MAX_MILLORSJUGADORS) && (!trobat));
	
	if (trobat)
	{
		// si la puntuacion entra en el top 5
		return (i - 1);
	}
	else
	{
		//si la puntuacio no entra en el top 5
		return -1;
	}
}

/*Procediment que desplaca el array del top 5 tantes posicions com el procediment anterior
hagi retornat, aquesta funcio es crida, nomes si la posicio entra en el top 5 (>=0)*/
void DesplacarArray(TipusJugador TMillorsJugadors[], int posicio)
{
	int i;
	int j = MAX_MILLORSJUGADORS - 1;
	int k = j - posicio;

	for (i = 0; i < k; i++)
	{
		TMillorsJugadors[j - i].puntuacio = TMillorsJugadors[j - 1 - i].puntuacio;
		strcpy_s(TMillorsJugadors[j-i].Nom, TMillorsJugadors[j-1-i].Nom);
	}
}

/* Procediment que demana el nom del jugador i guarda el nom i la puntuacio a la posicio
trobada amb la funcio ESMillorPuntuacio, seguint la condicio del anterior comentari*/
void EmplenarPosicioArray(TipusJugador TMillorsJugadors[], int Punts, int posicio)
{
	system("CLS");

	printf("\nPuntuacio Top 5!!!\n");
	printf("Entra el teu nom:\n");
	
	gets_s(TMillorsJugadors[posicio].Nom);

	(TMillorsJugadors[posicio]).puntuacio = Punts;
}

// Procediment que tan sols escriu el ranking per pantalla
void EscriuRanking(TipusJugador TMillorsJugadors[])
{
	int i;

	printf("---> Top 5 <---\n");
	for (i = 0; i < MAX_MILLORSJUGADORS; i++)
	{
		printf("Jugador: %s  Puntuacio: %d\n", TMillorsJugadors[i].Nom, TMillorsJugadors[i].puntuacio);
	}
	printf("\n");
}