#include "Graphics_Console.h"
#include <stdio.h>
#include <conio.h>
#define TECLA_ESC 27


// Inici del taulell
#define INICI_X 4
#define INICI_Y 6

// Fi del taulell
#define FI_X 36
#define FI_Y 23

// Codi diseny
#define GESPA 177
#define CAR 219

//Funcions definides en joc.cpp
void Joc();
void Taulell(HANDLE hScreen);
void InfoPuntsPartida(HANDLE hScreen);
void InfoVidesPartida(HANDLE hScreen);
void InfoNivellPartida(HANDLE hScreen);
void GameOver(HANDLE hScreen);

int Aleatori(int min, int max);
void PintarObjecte(int X, int Y, char Objecte, int ColorObj, HANDLE hScreen);
void EsborrarObjecte(int X, int Y, char Objecte, HANDLE hScreen);