#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <string.h>

typedef struct
{
	int numEstacio;
	char nomEstacio[256];
	int numMesures;
}miDato;

void estacion(int a, int mi_pipe[10][2], int pipeAnalisi[2])
{
	int mesures = 0;
	int randomNumber = 0;
	int max = 10;
	miDato midato[max];

	close(mi_pipe[a][1]);
	
	read(mi_pipe[a][0], &midato[a], sizeof(midato[a]));	
	
	int j;
	for(j = 0; j < midato[a].numMesures; j++)
	{
		srand(getpid());
		randomNumber = rand() % 51;
		mesures += randomNumber;
	}
	
	midato[a].numMesures = mesures / midato[a].numMesures;
	sleep(10);
	
	write(pipeAnalisi[1], &midato[a], sizeof(midato[a]));
}

void analisi(int pipeAnalisi[2])
{	
	int MAX = 10;
	miDato miEntrega[MAX];
	miDato guanyador;
	guanyador.numMesures = 10000000;
	FILE* file = fopen("mesures.txt", "w+");
	
	close(pipeAnalisi[1]);
	int k;
	for(k = 0; k < MAX; k++)
	{
		read(pipeAnalisi[0], &miEntrega[k], sizeof(miEntrega[k]));
		
		fprintf(file, "%d %s %d\n", miEntrega[k].numEstacio, miEntrega[k].nomEstacio, miEntrega[k].numMesures);
	}
	
	int r;
	for(r = 0; r < MAX; r++)
	{
		if(miEntrega[r].numMesures <= guanyador.numMesures)
		{
			guanyador.numMesures = miEntrega[r].numMesures; 
			strncpy(guanyador.nomEstacio, miEntrega[r].nomEstacio, sizeof(miEntrega[r].nomEstacio));
			guanyador.numEstacio = miEntrega[r].numEstacio;
		}
	}
	fprintf(file, "Numero: %d Nom: %s Temperatura: %d\n", guanyador.numEstacio, guanyador.nomEstacio, guanyador.numMesures);
	fclose(file);

	printf("\nNumero estacio: %d\n", guanyador.numEstacio);	
	printf("Nom estacio: %s\n", guanyador.nomEstacio);	
	printf("Temperatura mínima: %d\n\n", guanyador.numMesures);
}

int main(int argc, char** argv)
{	
	int pipeAnalisi[2];
	pipe(pipeAnalisi);
	
	int pid;
	pid = fork();
	
	switch(pid)
	{
		case -1:
			perror("Error al crear el analisi\n");
			exit(1);
		case 0:
			analisi(pipeAnalisi);
			exit(0);
		default: ;
			int max = 10;
			int mi_pipe[max][2];
			miDato midato[max];
			FILE* file = fopen("estacions.txt", "r+");

			int i;
			for(i = 0; i < max; i++)
			{
				pipe(mi_pipe[i]);
				int aux = fork();
				
				if(aux == -1)
				{
					perror("Error al crear estacion\n");
					exit(1);
				}
				else if (aux == 0)
				{
					estacion(i, mi_pipe, pipeAnalisi);
					exit(0);
				}
				else
				{
					if(fscanf(file, "%d %s %d", &midato[i].numEstacio, midato[i].nomEstacio, &midato[i].numMesures) > 0)
					{
						write(mi_pipe[i][1], &midato[i], sizeof(midato[i]));
					}
				}
			}
			fclose(file);

			int t;
			for(t = 0; t < max+1; t++)
			{
				wait(NULL);
			}
			exit(0);
	}
	return 0;
}
