#include <stdlib.h>
#include <stdio.h> // incluye print y scan


int main()
{
	FILE* file = fopen("archivo.txt", "r+");
	char buffer[200];
	int id, mesures;
	
	while(!feof(file))
	{
		if(fscanf(file, "%d %s %d", &id, buffer, &mesures) > 0)
		{
		printf("%d %d\n", id, mesures);
		}
	}
	
	return 0;
}
