#include <string.h>
#include <fstream>
#include <iostream>

using namespace std;

const int MAX_PLAYER_NAME = 15;
const int MAX_PLAYER_RANKING = 5;
const char nomFitxer[] = "ranking_jugadors.txt";

typedef struct
{
	char nom[MAX_PLAYER_NAME];
	int puntuacio;
} TipusJugador;


void iniciaTaulaMillorJugadors(TipusJugador TMillorsJugadors[]);
int haMilloratPuntuacio(TipusJugador TMillorsJugadors[], int punts);
void desplacaArray(TipusJugador TMillorsJugadors[], int posicio);
void emplenaPosicioArray(TipusJugador &TMillorsJugadors, int punts);
void mostraRanking();
void escriuRankingFitxer(TipusJugador TMillorsJugadors[MAX_PLAYER_RANKING]);
