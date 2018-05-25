#include "Joc.h"

/*
// Procediment que modifica la posicio del cotxe 2
void MoureCar2(int &X, int &Y, HANDLE hScreen)
{
	if (X < FI_X)
	{
		EsborrarObjecte(X, Y, CAR2, hScreen);
		X++;
		Y = Y;
		PintarObjecte(X, Y, CAR2, BLUE, hScreen);
	}
	else

	               .ooooooo.               .oooooo. \n               
                  OOOOOOOOOOo             oOOOOOOOOOo  \n            
                .OOOO    OOOOO.         .OOOO   | OOOO  \n           
                OOOO     |OOOOO.       .OOOO    | OOOO   \n          
                OOOO\___/oOOOOOOoOOOOOoOOOOO___/ oOOO'     \n        
                OOOOOoooOOOOOOOOOOOOOOOOOOOOOooOOOOO'      \n        
              oOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO.       \n      
            oOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO.        \n    
          .OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO.         \n  
.oOOo.   .OOOOOOOOOOOOO _______________ OOOOOOOOOOOOOOOO.     .oOo.\n
OOOOOOo..OOOOOOOOO>oooo| 1 Configurar  |ooooooooo<OOOOOOO.  .oOOOOO\n
OOOOoOOoOOOOOOOOOOOOOOO| 3 Puntuacions |OOOOOOOOOOOOOOOOOOo.oOOoOOOO\n
`OOOOOoOOOOOOOOOOOOOOOO| 4 Modes de Joc|OOOOOOOOOOOOOOOOOOOOOoOOOOOO\n
 `OOOOOOoOOOOOOOOOOOoOO| 5  Sortir     |OOOOOOOOoOOOOOOOOOoOOOOOOO'\n
  `OOOOOOoOOOOOOOOOOOoO --------------- OOOOOOOoOOOOOOOOOoOOOOOO'  \n
    `OOOOOoOOOOOoOOOOOoOOOOOOOOOOOOOOOOOOOOOOOoOOOOoOOOoOOOOOO'    \n
      `OOOOoOOOOOoOOOOOoOOOOOOOOOOOOOOOOOOOOoOOOOoOOOOoOOOOO'     \n 
       ooOOOoOOOOOOoOOOOoOOOOOOOOOOOOOOOOOOoOOOOoOOOOoOOOO'       \n 
    .oOOOOOOO    `OOoOOOOoOOOOOOOOOOOOOOOOoOOOoOOOOoOOOOo.      \n   
 _oOOOOOOOOOO   _______OOOO_       ______OOOO_     `OOOOOOOOOOoo. \n 
|OOO OO' OOO'| |      .OOOOOo     /    oOOOO  \     /OOO  OOO  OOO.\n
|OO' O'__O'__| |      OO OO OO   /    OO_OO O  \   / OO'  _`OO   `O\n
|`O   |        |     |O'\O' `O| |     O' \O `O  | |  O'  /  \O    |\n
|     |_____   |     |__/     | |     |   |     | |     |    |____|\n
|           |  |             /  |     |   |     | |     |  _______ \n
|      _____|  |            /   |     |   |     | |     | |__     |\n
|     |        |     |\     \   |     |   |     | |     |    |    |\n
|     |        |     | \     \  |      \_/      | |      \__/     |\n
|     |        |     |  |     |  \             /   \             / \n
|_____|        |_____|  |_____|   \___________/     \___________/  \n

	{
		EsborrarObjecte(X, Y, CAR2, hScreen);
		X = INICI_X;
		Y = Aleatori(INICI_Y + 4, FI_Y - 2);
		PintarObjecte(X, Y, CAR2, BLUE, hScreen);
	}
}*/

// Procediment que modifica la posicio de Frog2 quan es mou
void MoureFrog2(int &X, int &Y, int DirX, int DirY, HANDLE hScreen)
{
	if ((X + DirX <= FI_X) && (X + DirX >= INICI_X) && (Y + DirY <= FI_Y))
	{
		if ((Y <= FI_Y) && (Y > (FI_Y - 2)))
		{
			EsborrarObjecte(X, Y, FROG2, hScreen);
			PintarObjecte(X, Y, GESPA, LIGHTGREEN, hScreen);
			X = X + DirX;
			Y = Y + DirY;
			PintarObjecte(X, Y, FROG2, BROWN, hScreen);
		}
		else
		{
			EsborrarObjecte(X, Y, FROG2, hScreen);
			X = X + DirX;
			Y = Y + DirY;
			PintarObjecte(X, Y, FROG2, BROWN, hScreen);
		}
	}
}

// Procediment que mostra Nivell Superat al centre de la pantalla
void NivellSuperat2(HANDLE hScreen)
{
	TextColor(MAGENTA, BLACK, hScreen);
	int meitat_X = ((INICI_X + FI_X) / 2) - INICI_X - 2;
	int meitat_Y = ((INICI_Y + FI_Y) / 2) + 1;
	GotoXY(meitat_X, meitat_Y, hScreen);
	printf("NIVELL SUPERAT");
}

// Procediment per a estalviar linies de codi que elimina la partida actual
void BorrarPartida(int &Frog2X, int &Frog2Y, int &FrogX, int &FrogY, int &CarX, int &CarY, int &Car2X, int &Car2Y, HANDLE hScreen)
{
	Sleep(500);
	EsborrarObjecte(Frog2X, Frog2Y, FROG2, hScreen);
	EsborrarObjecte(FrogX, FrogY, FROG, hScreen);
	EsborrarObjecte(CarX, CarY, CAR, hScreen);
	EsborrarObjecte(Car2X, Car2Y, CAR2, hScreen);
}

// Procediment semblant al anterior. Aquest crea una nova partida
void NovaPartida2(int &Punts, int &Punts2, int &Vides, int &Vides2, int nivell, int &Frog2X, int &Frog2Y, int &FrogX, int &FrogY, int &CarX, int &CarY, int &Car2X, int &Car2Y, HANDLE hScreen)
{
	Taulell(hScreen);
	InfoPuntsPartida(Punts, hScreen);
	InfoPuntsPartida2(Punts2, hScreen);
	InfoVidesPartida(Vides, hScreen);
	InfoVidesPartida2(Vides2, hScreen);
	InfoNivellPartida(nivell, hScreen);

	CarX = INICI_X;
	CarY = Aleatori(INICI_Y + 4, FI_Y - 2);
	Car2X = INICI_X;
	Car2Y = Aleatori(INICI_Y + 4, FI_Y - 2);
	FrogX = Aleatori(INICI_X, FI_X);
	FrogY = FI_Y;
	Frog2X = Aleatori(INICI_X, FI_X);
	Frog2Y = FI_Y;

	PintarObjecte(CarX, CarY, CAR, RED, hScreen);
	PintarObjecte(FrogX, FrogY, FROG, YELLOW, hScreen);
	PintarObjecte(Frog2X, Frog2Y, FROG2, BROWN, hScreen);
}

// Procediment que mostra les vides del jugador 2, les vides del jugador 1 son mostrades amb el procediment definita a joc.cpp
void InfoVidesPartida2(int Vides, HANDLE hScreen)
{
	TextColor(LIGHTGREY, BLACK, hScreen);
	GotoXY(INICI_X , INICI_Y - 1, hScreen);
	printf("P2 VIDES: %d", Vides);
}

// Procediment que nomes mostra els punts del jugador 2
void InfoPuntsPartida2(int Punts, HANDLE hScreen)
{
	TextColor(LIGHTGREY, BLACK, hScreen);
	GotoXY(INICI_X, INICI_Y - 3, hScreen);
	printf("P2 PUNTS: %d", Punts);
}

// Funcio de joc 2 per a 2 jugadors
void Joc2()
{
	PlaySound(TEXT("C:\\Users\\Lambo\\Desktop\\frog_ruben_ramon\\frog\\M�SIC\\dosjugadores.wav"), NULL, /*music start esto*/ SND_FILENAME | /* music a la vez que c�digo*/ SND_ASYNC);

	HANDLE hScreen;
	char tecla = 'B';
	int CarX, CarY, Car2X, Car2Y, FrogX, FrogY, Frog2X, Frog2Y;
	int velocitatJoc = 1000, contCar = velocitatJoc, nivell = 1, Punts = 0, Punts2 = 0, Vides = 3, Vides2 = 3;

	srand((unsigned)time(NULL));

	hScreen = GetStdHandle(STD_OUTPUT_HANDLE);
	InitScreen(hScreen);

	NovaPartida2(Punts, Punts2, Vides, Vides2, nivell, Frog2X, Frog2Y, FrogX, FrogY, CarX, CarY, Car2X, Car2Y, hScreen);

	do
	{
		if (_kbhit())
		{
			tecla = _getch();

			switch (tecla)
			{
			case TECLA_ADALT:
				MoureFrog(FrogX, FrogY, 0, -1, hScreen);
				break;
			case TECLA_DRETA:
				MoureFrog(FrogX, FrogY, 1, 0, hScreen);
				break;
			case TECLA_ESQUERRA:
				MoureFrog(FrogX, FrogY, -1, 0, hScreen);
				break;
			case TECLA_ABAIX:
				MoureFrog(FrogX, FrogY, 0, 1, hScreen);
				break;
			case arriba:
				MoureFrog2(Frog2X, Frog2Y, 0, -1, hScreen);
				break;
			case derecha:
				MoureFrog2(Frog2X, Frog2Y, 1, 0, hScreen);
				break;
			case izquierda:
				MoureFrog2(Frog2X, Frog2Y, -1, 0, hScreen);
				break;
			case abajo:
				MoureFrog2(Frog2X, Frog2Y, 0, 1, hScreen);
				break;
			default:
				break;
			}
		}
			
		contCar--;
		if (contCar == 0)
		{
			MoureCar(CarX, CarY, hScreen);
			MoureCar(Car2X, Car2Y, hScreen);

			contCar = velocitatJoc/nivell;
			Sleep(1);
		}

		if (Frog2Y <= (INICI_Y + 3))
		{
			Punts2 += 100 * nivell;
			nivell++;
			NivellSuperat2(hScreen);

			BorrarPartida(Frog2X, Frog2Y, FrogX, FrogY, CarX, CarY, Car2X, Car2Y, hScreen);

			NovaPartida2(Punts, Punts2, Vides, Vides2, nivell, Frog2X, Frog2Y, FrogX, FrogY, CarX, CarY, Car2X, Car2Y, hScreen);

		}

		if (FrogY <= (INICI_Y + 3))
		{
			Punts += 100 * nivell;
			nivell++;
			NivellSuperat2(hScreen);

			BorrarPartida(Frog2X, Frog2Y, FrogX, FrogY, CarX, CarY, Car2X, Car2Y, hScreen);

			NovaPartida2(Punts, Punts2, Vides, Vides2, nivell, Frog2X, Frog2Y, FrogX, FrogY, CarX, CarY, Car2X, Car2Y, hScreen);

		}
		if ((Frog2Y == CarY) && (Frog2X == CarX) || (Frog2Y == Car2Y) && (Frog2X == Car2X))
		{
			Vides2--;

			BorrarPartida(Frog2X, Frog2Y, FrogX, FrogY, CarX, CarY, Car2X, Car2Y, hScreen);

			NovaPartida2(Punts, Punts2, Vides, Vides2, nivell, Frog2X, Frog2Y, FrogX, FrogY, CarX, CarY, Car2X, Car2Y, hScreen);

			if (Vides2 == 0)
			{
				if (Punts >= Punts2)
				{
					TextColor(LIGHTRED, BLACK, hScreen);
					int meitat_X = ((INICI_X + FI_X) / 2) - INICI_X;
					int meitat_Y = ((INICI_Y + FI_Y) / 2) + 1;
					GotoXY(meitat_X, meitat_Y, hScreen);
					printf("PLAYER ONE WIN");
				}
				else
				{
					TextColor(LIGHTRED, BLACK, hScreen);
					int meitat_X = ((INICI_X + FI_X) / 2) - INICI_X;
					int meitat_Y = ((INICI_Y + FI_Y) / 2) + 1;
					GotoXY(meitat_X, meitat_Y, hScreen);
					printf("PLAYER TWO WIN");
				}
			}
		}
		if ((FrogY == CarY) && (FrogX == CarX) || (FrogY == Car2Y) && (FrogX == Car2X))
		{
			Vides--;
			BorrarPartida(Frog2X, Frog2Y, FrogX, FrogY, CarX, CarY, Car2X, Car2Y, hScreen);

			NovaPartida2(Punts, Punts2, Vides, Vides2, nivell, Frog2X, Frog2Y, FrogX, FrogY, CarX, CarY, Car2X, Car2Y, hScreen);

			if (Vides == 0)
			{
				if ( Punts >= Punts2)
				{
					TextColor(LIGHTRED, BLACK, hScreen);
					int meitat_X = ((INICI_X + FI_X) / 2) - INICI_X;
					int meitat_Y = ((INICI_Y + FI_Y) / 2) + 1;
					GotoXY(meitat_X, meitat_Y, hScreen);
					printf("PLAYER ONE WIN");
				}
				else
				{
					TextColor(LIGHTRED, BLACK, hScreen);
					int meitat_X = ((INICI_X + FI_X) / 2) - INICI_X;
					int meitat_Y = ((INICI_Y + FI_Y) / 2) + 1;
					GotoXY(meitat_X, meitat_Y, hScreen);
					printf("PLAYER TWO WIN");
				}
					
			}
		}

	} while ((Vides > 0) && (Vides2 > 0) && (tecla != TECLA_ESC));

	PlaySound(NULL, 0, 0); //parar music
	
	TextColor(WHITE, BLACK, hScreen);
	GotoXY(INICI_X, FI_Y + 2, hScreen);
	system("PAUSE");
}