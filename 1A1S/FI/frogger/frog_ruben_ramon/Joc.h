// Fitxers de capcalera necessaris
#include "Graphics_Console.h"
#include <stdio.h>
#include <conio.h>
#include <stdlib.h>
#include <time.h>
#include <Windows.h>
#include <mmsystem.h>

//Variable de objecte dintre del joc
typedef struct
{
	int posX;
	int posY;
}Objecte;


// Tecles utilitzades per jugar
#define TECLA_ESC 27
#define TECLA_DRETA 77
#define TECLA_ESQUERRA 75
#define TECLA_ADALT 72
#define TECLA_ABAIX 80


// Inici del taulell
#define INICI_X 4
#define INICI_Y 6

// Fi del taulell
#define FI_X 36
#define FI_Y 23

// Codi de diseny
#define GESPA 177
#define CAR 219
#define FROG 153

//Procediments i funcions creades a joc.cpp
int Joc(int nivell);
void Taulell(HANDLE hScreen);
void InfoPuntsPartida(int Punts, HANDLE hScreen);
void InfoVidesPartida(int Vides, HANDLE hScreen);
void InfoNivellPartida(int nivell, HANDLE hScreen);
void GameOver(HANDLE hScreen);

int Aleatori(int min, int max);
void PintarObjecte(int X, int Y, char Objecte, int ColorObj, HANDLE hScreen);
void EsborrarObjecte(int X, int Y, char Objecte, HANDLE hScreen);

void MoureCar(int &X, int &Y, HANDLE hScreen);
void MoureFrog(int &X, int &Y, int DirX, int DirY, HANDLE hScreen);

void SumaPunts(int &punts, int &nivell);
void NivellSuperat(int &punts, int &nivell, HANDLE hScreen);

void NovaPartida(int &frogX, int &frogY, int &carX, int &carY, int &Punts, int &Vides, int &nivell, HANDLE hScreen);



// Fucnions, procediments i constants per a joc 2

#define CAR2 220
#define FROG2 143

#define arriba 119
#define derecha 100
#define izquierda 97
#define abajo 115

void Joc2();
void MoureCar2(int &X, int &Y, HANDLE hScreen);
void MoureFrog2(int &X, int &Y, HANDLE hScreen);
void InfoVidesPartida2(int Vides, HANDLE hScreen);
void InfoPuntsPartida2(int Punts, HANDLE hScreen);
void NivellSuperat2(HANDLE hScreen);
void BorrarPartida(int &Frog2X, int &Frog2Y, int &FrogX, int &FrogY, int &CarX, int &CarY, int &Car2X, int &Car2Y, HANDLE hScreen);
void NovaPartida2(int &Punts, int &Punts2, int &Vides, int &Vides2, int nivell, int &Frog2X, int &Frog2Y, int &FrogX, int &FrogY, int &CarX, int &CarY, int &Car2X, int &Car2Y, HANDLE hScreen);