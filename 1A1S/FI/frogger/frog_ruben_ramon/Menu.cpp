#include "Menu.h"
#include <Windows.h>
#include <mmsystem.h>

// Procediment que mostra el menu principal
void MenuPrincipal()
{
	printf("	      (.)~(.)\n");
	printf("	     (-------)\n");
	printf("	 ---ooO-----Ooo----\n");
	printf("	|...1- Jugar.......|\n");
	printf("	|...2- Configurar..|\n");
	printf("	|...3- Puntuacions.|\n");
	printf("	|...4- Modes de Joc|\n");
	printf("	|...5- Sortir......|\n");
	printf("	|------------------|\n");
	printf("	    ( )   ( ) \n");
	printf("	    /|\    /|\ \n");
}

// Procediment que mostra els menu de modes de joc, alguns encara estan en fase BEta
void MenuModes()
{
	printf("----------------|> Modes de Joc <|----------------\n");
	printf("	1- 2 Player & 2 Cars\n");
	printf("	2- 2 Cars - DLC No disponible\n");
	printf("	3- 2 Hard - DLC No disponible\n");
	printf("	4- Sortir\n");
	printf("--------------------------------------------------\n");
	PlaySound(NULL, 0, 0); //parar music
}

// Procediment que mostra els modes de joc que pots escollir
void MenuNivellDificultat()
{
	printf("-----|> Dificultat <|-----\n");
	printf("	1- Principiant\n");
	printf("	2- Mig\n");
	printf("	3- Dificil\n");
	printf("----------------------------\n");
	
}