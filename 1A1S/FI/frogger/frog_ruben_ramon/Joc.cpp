#include "Joc.h"

// Funcio generadora de un numero aleatori entre un minim i un maxim retornant el numero aleatori
int Aleatori(int min, int max)
{
	int range = max - min;
	int aux = rand() % (range + 1) + min;
	return aux;
}

//Procediment del moviment del cotxe de esquerra a dreta
void MoureCar(int &X, int &Y, HANDLE hScreen)
{
	// mentre no arribi al maxim de la dreta del taulell
	if (X < FI_X)
	{
		EsborrarObjecte(X, Y, CAR, hScreen);
		X++;
		Y = Y;
		PintarObjecte(X, Y, CAR, RED, hScreen);
	}
	else
	{
		// si arriba a la dreta del taulell
		EsborrarObjecte(X, Y, CAR, hScreen);
		X = INICI_X;
		Y = Aleatori(INICI_Y + 4, FI_Y - 2);
		PintarObjecte(X, Y, CAR, RED, hScreen);
	}
}

// Procediment per moure la granota dintre del taulell
void MoureFrog(int &X, int &Y, int DirX, int DirY, HANDLE hScreen)
{
	// sempre i quan la posicio de la granota mes la direccio estigui dintre del taulell
	if ((X + DirX <= FI_X) && (X + DirX >= INICI_X) && (Y + DirY <= FI_Y))
	{
		if ((Y <= FI_Y) && (Y > (FI_Y - 2)))
		{
			// si esta a la gespa
			EsborrarObjecte(X, Y, FROG, hScreen);
			PintarObjecte(X, Y, GESPA, LIGHTGREEN, hScreen);
			X = X + DirX;
			Y = Y + DirY;
			PintarObjecte(X, Y, FROG, YELLOW, hScreen);
		}
		else
		{
			EsborrarObjecte(X, Y, FROG, hScreen);
			X = X + DirX;
			Y = Y + DirY;
			PintarObjecte(X, Y, FROG, YELLOW, hScreen);
		}
	}
}

// Procediment que suma els punts i augmenta el nivell
void SumaPunts(int &punts, int &nivell)
{
	punts += 100 * nivell;
	nivell++;
}

// Procediment que mostra nivell superat i actualitza els punts
void NivellSuperat(int &punts, int &nivell, HANDLE hScreen)
{
	SumaPunts(punts, nivell);
	TextColor(MAGENTA, BLACK, hScreen);
	int meitat_X = ((INICI_X + FI_X) / 2) - INICI_X - 2;
	int meitat_Y = ((INICI_Y + FI_Y) / 2) + 1;
	GotoXY(meitat_X, meitat_Y, hScreen);
	printf("NIVELL SUPERAT");
	Sleep(900);
}

// Procediment utilitzat per a pintar un objecte
void PintarObjecte(int X, int Y, char Objecte, int ColorObj, HANDLE hScreen)
{
	TextColor(ColorObj, BLACK, hScreen);
	GotoXY(X, Y, hScreen);
	printf("%c", Objecte);
}

// Procediment per a esborrar un objecte, invers del procediment anterior
void EsborrarObjecte(int X, int Y, char Objecte, HANDLE hScreen)
{
	TextColor(BLACK, BLACK, hScreen);
	GotoXY(X, Y, hScreen);
	printf("%c", Objecte);
}

// Procediment que mostra els punts de la partida
void InfoPuntsPartida(int Punts, HANDLE hScreen)
{
	TextColor(LIGHTGREY, BLACK, hScreen);
	GotoXY(INICI_X, INICI_Y - 4, hScreen);
	printf("P1 PUNTS: %d", Punts);
}

// Procediment que mostra les vides restants
void InfoVidesPartida(int Vides, HANDLE hScreen)
{
	TextColor(LIGHTGREY, BLACK, hScreen);
	GotoXY(INICI_X, INICI_Y - 2, hScreen);
	printf("P1 VIDES: %d", Vides);
}

// Procediment que mostra el nivell actual de la partida
void InfoNivellPartida(int nivell, HANDLE hScreen)
{
	TextColor(LIGHTGREY, BLACK, hScreen);
	GotoXY(FI_X - 8, INICI_Y - 4, hScreen);
	printf("NIVELL: %d", nivell);
}

// Procediment que mostra game over
void GameOver(HANDLE hScreen)
{
	TextColor(RED, BLACK, hScreen);
	int meitat_X = ((INICI_X + FI_X) / 2) - INICI_X;
	int meitat_Y = ((INICI_Y + FI_Y) / 2) + 1;
	GotoXY(meitat_X, meitat_Y, hScreen);
	printf("GAME OVER");
}

// Procediment de crear una partida nova
void NovaPartida(int &frogX, int &frogY, int &carX, int &carY, int &Punts, int &Vides, int &nivell, HANDLE hScreen)
{
	Sleep(100);

	EsborrarObjecte(frogX, frogY, FROG, hScreen);
	EsborrarObjecte(carX, carY, CAR, hScreen);

	Taulell(hScreen);
	InfoPuntsPartida(Punts, hScreen);
	InfoVidesPartida(Vides, hScreen);
	InfoNivellPartida(nivell, hScreen);

	carX = INICI_X;
	carY = Aleatori(INICI_Y + 4, FI_Y - 2);
	frogX = Aleatori(INICI_X, FI_X);
	frogY = FI_Y;

	PintarObjecte(carX, carY, CAR, RED, hScreen);
	PintarObjecte(frogX, frogY, FROG, YELLOW, hScreen);
}

//Procediment de creacio del taulell
void Taulell(HANDLE hScreen)
{
	int i;

	system("CLS");

	TextColor(LIGHTGREEN, BLACK, hScreen);

	for (i = INICI_X; i <= FI_X; i++)
	{
		GotoXY(i, INICI_Y + 1, hScreen);
		printf("%c", GESPA);
		GotoXY(i, INICI_Y + 2, hScreen);
		printf("%c", GESPA);
		GotoXY(i, INICI_Y + 3, hScreen);
		printf("%c", GESPA);
		GotoXY(i, FI_Y, hScreen);
		printf("%c", GESPA);
		GotoXY(i, FI_Y - 1, hScreen);
		printf("%c", GESPA);
	}
}

//Procediment de joc
int Joc(int nivell)
{
	PlaySound(NULL, 0, 0); //parar music
	PlaySound(TEXT("C:\\Users\\Lambo\\Desktop\\frog_ruben_ramon\\frog\\MÚSIC\\AdhesiveWombat.wav"), NULL, /*music start esto*/ SND_FILENAME | /* music a la vez que código*/ SND_ASYNC);
	
	// declaracio i definicio de variables
	HANDLE hScreen;
	char tecla = 'B';
	Objecte Car, Frog;
	int velocitatJoc = 1000, contCar = velocitatJoc, Punts = 0, Vides = 3;

	srand((unsigned)time(NULL));

	hScreen = GetStdHandle(STD_OUTPUT_HANDLE);
	InitScreen(hScreen);

	Taulell(hScreen);
	InfoPuntsPartida(Punts, hScreen);
	InfoVidesPartida(Vides, hScreen);
	InfoNivellPartida(nivell, hScreen);

	Car.posX = INICI_X;
	Car.posY = Aleatori(INICI_Y + 4, FI_Y - 2);
	Frog.posX = Aleatori(INICI_X, FI_X);
	Frog.posY = FI_Y;

	PintarObjecte(Car.posX, Car.posY, CAR, RED, hScreen);
	PintarObjecte(Frog.posX, Frog.posY, FROG, YELLOW, hScreen);

	do
	{
		if (_kbhit())
		{
			tecla = _getch();

			switch (tecla)
			{
			case TECLA_ADALT:
				MoureFrog(Frog.posX, Frog.posY, 0, -1, hScreen);
				break;
			case TECLA_DRETA:
				MoureFrog(Frog.posX, Frog.posY, 1, 0, hScreen);
				break;
			case TECLA_ESQUERRA:
				MoureFrog(Frog.posX, Frog.posY, -1, 0, hScreen);
				break;
			case TECLA_ABAIX:
				MoureFrog(Frog.posX, Frog.posY, 0, 1, hScreen);
				break;
			default:
				break;
			}
		}
		contCar--;
		if (contCar == 0)
		{
			MoureCar(Car.posX, Car.posY, hScreen);
			contCar = velocitatJoc / nivell ;
			Sleep(1);
		}

		if (Frog.posY <= (INICI_Y + 3))
		{
			NivellSuperat(Punts, nivell, hScreen);
			
			NovaPartida(Frog.posX, Frog.posY, Car.posX, Car.posY, Punts, Vides, nivell, hScreen);
		}

		if ((Frog.posY == Car.posY) && (Frog.posX == Car.posX))
		{
			Vides--;
			NovaPartida(Frog.posX, Frog.posY, Car.posX, Car.posY, Punts, Vides, nivell, hScreen);

			PlaySound(TEXT("C:\\Users\\Lambo\\Desktop\\frog_ruben_ramon\\frog\\MÚSIC\\chocar.wav"), NULL, /*music start esto*/ SND_FILENAME | /* music a la vez que código*/ SND_ASYNC);
			Sleep(1100);
			PlaySound(TEXT("C:\\Users\\Lambo\\Desktop\\frog_ruben_ramon\\frog\\MÚSIC\\AdhesiveWombat.wav"), NULL, /*music start esto*/ SND_FILENAME | /* music a la vez que código*/ SND_ASYNC);

			if (Vides <= 0)
			{
				GameOver(hScreen);
				PlaySound(TEXT("C:\\Users\\Lambo\\Desktop\\frog_ruben_ramon\\frog\\MÚSIC\\Abucheowmv.wav"), NULL, /*music start esto*/ SND_FILENAME | /* music a la vez que código*/ SND_ASYNC);
				Sleep(200);
			}
		}

	} while ((Vides > 0) && (tecla != TECLA_ESC));

	PlaySound(NULL, 0, 0); //parar music

	TextColor(WHITE, BLACK, hScreen);
	GotoXY(INICI_X, FI_Y + 2, hScreen);
	system("PAUSE");


	return (Punts);
}