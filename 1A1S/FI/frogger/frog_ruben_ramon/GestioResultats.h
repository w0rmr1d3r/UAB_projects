#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <Windows.h>
#include <mmsystem.h>

// Constant del numero maxim de jugadors amb millor puntuacions
#define MAX_MILLORSJUGADORS 5
#define MAX_CARACTERS 15

// Estructura que defineix el tipus de jugador, amb el nom i la seva puntuacio
typedef struct 
{
	char Nom[MAX_CARACTERS];
	int puntuacio;
}TipusJugador;

// Declaracio de funcions i procediments creats a GestioResultats.cpp

void InicialitzarTaulaMillorsJugadors(TipusJugador TMillorsJugadors[]);
int EsMillorPuntuacio(TipusJugador TMillorsJugadors[], int Punts);
void DesplacarArray(TipusJugador TMillorsJugadors[], int posicio);
void EmplenarPosicioArray(TipusJugador TMillorsJugadors[], int Punts, int posicio);
void EscriuRanking(TipusJugador TMillorsJugadors[]);