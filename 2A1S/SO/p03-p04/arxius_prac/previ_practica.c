#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>


int main(int argc, char** argv)
{
	int mi_pipe[2];
	int pid, status, number;
	
	// Argumentos
	number = atoi(argv[1]);
	
	// Pipe
	pipe(mi_pipe);
	
	pid = fork();
	
	switch (pid)
	{
		case -1:
			perror("Error al crear proceso hijo 1");
			exit(1);
			
		case 0: ; // empty statement
		// fill 1
			int leido = 0;
			close(mi_pipe[1]);
			read(mi_pipe[0], &leido, sizeof(int));
			printf("he acabado de leer en pipe (hijo1)\n");
			printf("%d\n", leido);
			
			exit(0);
		
		default: ; //empty statement
			int mi_pipe_2[2];
			pipe(mi_pipe_2);
			int pid_2 = fork();
		
			switch (pid_2)
			{
				case -1:
					perror("Error al crear proceso hijo 2");
					exit(1);
				
				case 0: ; //empty statement
				// fill 2
					int leido = 0;
					close(mi_pipe_2[1]);
					read(mi_pipe_2[0], &leido, sizeof(int));
					printf("he acabado de leer en pipe (hijo2)\n");
					printf("%d\n", leido);
					
					exit(0);
			
				default:
				// pare
					close(mi_pipe[0]);
					close(mi_pipe_2[0]);
			
					write(mi_pipe[1], &number, sizeof(int));
					write(mi_pipe_2[1], &number, sizeof(int));
					printf("PADRE::he acabado de escribir en pipe\n");			
					wait(&status);
					wait(&status);
					exit(0);
			}
	}
	return 0;
}
