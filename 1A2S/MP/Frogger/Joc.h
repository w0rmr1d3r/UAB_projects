/**
 * Joc interactiu de la granota.
 */

#include <conio.h>      /* getch */ 
#include <stdio.h>      /* printf */
#include <time.h>       /* time */ 
#include <stdlib.h>		/* rand, srand*/
#include "lib\keyboard_sdl.h"	/* keyboard_left, right, ...*/
#include "lib\libreria.h"
#include "lib\event.h"
#include "lib\Grafic.h"
#include "Pantalla.h"
#include "Granota.h"
#include "Vehicle.h"

// Constants utilitzades per a no usar numeros "magics"
const int MAX_NIVELL = 3;
const int MAX_VIDES = 5;

int juga(int nivell);