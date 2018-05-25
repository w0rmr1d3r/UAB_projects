#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

#include <sys/types.h>

#include <unistd.h>
#include <sys/wait.h>
#include <string.h>

#define TAM_BUFFER 5
// true 1
// false 0

pthread_mutex_t mutex;
pthread_cond_t pet_ready;

int pet_esp = 0;
int pet_priority = 0;
int pet_buffer = 0;

typedef struct{
	int id_cliente; // identificador cliente 1->N
	int id_compra;  // identificador compra 1->100 generara 100 peticiones
} peticion_t;

typedef struct{
	int pos_lectura;	// siguiente posicion a leer
	int pos_escritura;  // siguiente posicion a escribir
	int num_peticiones; // numero de peticiones al buffer
	peticion_t peticiones[TAM_BUFFER]; // buffer de peticiones (circular)
} buffer_peticiones_t;

buffer_peticiones_t buffer;
buffer_peticiones_t priority_buffer;

void buffer_peticiones_inicializar(buffer_peticiones_t* buffer_peticiones){
	buffer_peticiones->pos_lectura = 0;
	buffer_peticiones->pos_escritura = 0;
	buffer_peticiones->num_peticiones = 0;
	}

int buffer_peticiones_lleno(buffer_peticiones_t* buffer_peticiones){
	return buffer_peticiones->num_peticiones==TAM_BUFFER;
	}

int buffer_peticiones_vacio(buffer_peticiones_t* buffer_peticiones){
	return buffer_peticiones->num_peticiones==0;
	}

void buffer_peticiones_encolar(
buffer_peticiones_t* buffer_peticiones,peticion_t* peticion){
	buffer_peticiones->peticiones[buffer_peticiones->pos_escritura] = *peticion;
	buffer_peticiones->pos_escritura=(buffer_peticiones->pos_escritura+1)%TAM_BUFFER;
	++buffer_peticiones->num_peticiones;
	}

void buffer_peticiones_atender(
buffer_peticiones_t* buffer_peticiones,peticion_t* peticion){
	*peticion = buffer_peticiones->peticiones[buffer_peticiones->pos_lectura];
	buffer_peticiones->pos_lectura = (buffer_peticiones->pos_lectura+1)%TAM_BUFFER;
	--buffer_peticiones->num_peticiones;
	}

void* producer(long int mi_id) 
{	
	peticion_t mi_peticion;
	int election;
	int i;
	srand(time(0));
	for(i = 1;i<=100;)
	{
		//printf("cliente %ld genera peticion %d\n", mi_id, i);
		
		election = rand()%2;
		
		mi_peticion.id_cliente = mi_id;
		mi_peticion.id_compra = i;
		
		pthread_mutex_lock(&mutex);
		if(buffer_peticiones_lleno(&buffer) == 0 && election == 0)
		{
			buffer_peticiones_encolar(&buffer,&mi_peticion);
			//printf("cliente %ld encola peticion %d en buffer\n", mi_id, i);
			++pet_buffer;
			i++;
		}
		else if(buffer_peticiones_lleno(&priority_buffer) == 0 && election == 1)
		{
			buffer_peticiones_encolar(&priority_buffer,&mi_peticion);
			//printf("cliente %ld encola peticion %d en priority\n", mi_id, i);
			++pet_priority;
			i++;
		}
		else
		{
			++pet_esp;
			pthread_cond_wait(&pet_ready, &mutex);
			--pet_esp;
		}
		pthread_mutex_unlock(&mutex);
	}
	
	pthread_exit(0);
}

void* consumer(long int j) 
{
	FILE* file = fopen("compras.txt", "w+");
	peticion_t mi_peticion;

	while(1)
	{
		//printf("consumidor %ld a mutex\n", j);
		pthread_mutex_lock(&mutex);
		if(pet_priority > 0)
		{
			if(buffer_peticiones_vacio(&priority_buffer) == 0)
			{
				buffer_peticiones_atender(&priority_buffer, &mi_peticion);
				//printf("consumidor %ld atiende priority\n", j);
				//printf("Cliente-%d %d\n", mi_peticion.id_cliente, mi_peticion.id_compra);
				--pet_priority;
				if(pet_esp > 0)
				{
					pthread_cond_signal(&pet_ready);
				}
				if(mi_peticion.id_cliente == 0 && mi_peticion.id_compra == 0)
				{
					printf("consumidor %ld finaliza\n", j);
					break;
				}
				fprintf(file, "Cliente-%d %d\n", mi_peticion.id_cliente, mi_peticion.id_compra);
			}
		}
		else 
		{
			if(pet_buffer > 0)
			{
				if(buffer_peticiones_vacio(&buffer) == 0)
				{
					buffer_peticiones_atender(&buffer, &mi_peticion);
					//printf("consumidor %ld atiende buffer\n", j);
					//printf("Cliente-%d %d\n", mi_peticion.id_cliente, mi_peticion.id_compra);
					--pet_buffer;
					if(pet_esp > 0)
					{
						pthread_cond_signal(&pet_ready);
					}
					if(mi_peticion.id_cliente == 0 && mi_peticion.id_compra == 0)
					{
						printf("consumidor %ld finaliza\n", j);
						break;
					}
					fprintf(file, "Cliente-%d %d\n", mi_peticion.id_cliente, mi_peticion.id_compra);
				}
			}	
		}
		//printf("consumidor %ld fuera de mutex\n", j);
		pthread_mutex_unlock(&mutex);
	}
	
	fclose(file);
	
	pthread_exit(0);
}

int main(int argc,char** argv) 
{
	pthread_mutex_init(&mutex, NULL);
	pthread_cond_init(&pet_ready, NULL);
	
	buffer_peticiones_inicializar(&buffer);
	buffer_peticiones_inicializar(&priority_buffer);
	
	peticion_t ult_peticion;
	ult_peticion.id_cliente= 0;
	ult_peticion.id_compra = 0;
	
	
	long int j;
	printf("Inserte numero de clientes:\n");
	scanf("%ld", &j);
	
	long int k;
	printf("Inserte numero de servidores:\n");
	scanf("%ld", &k);
	
	
	pthread_t cliente[j];
	pthread_t servidor[k];
	
	long int i;
	for(i = 0; i<j;i++)
	{
		pthread_create(cliente+i,NULL,(void* (*)(void*))producer,(void*)(i));
	}
	
	for(i = 0; i<k;i++)
	{
		pthread_create(servidor+i,NULL,(void* (*)(void*))consumer,(void*)(i));
	}	
	//printf("servidores y clientes creados correctamente\n");

	int* exit_status;
	for (i=0;i<j;++i) 
	{
		pthread_join(cliente[i],(void**)&exit_status);
		free(exit_status);
	}
	//printf("clientes finalizados\n");
	
	for (i=0;i<k;) 
	{
		pthread_mutex_lock(&mutex);
		if(buffer_peticiones_lleno(&priority_buffer) == 0)
		{
			buffer_peticiones_encolar(&priority_buffer,&ult_peticion);
			printf("master encola ult_peticion para %ld en buffer\n", i);
			++pet_buffer;
			++i;
		}
		else
		{
			++pet_esp;
			pthread_cond_wait(&pet_ready, &mutex);
			--pet_esp;
		}
		pthread_mutex_unlock(&mutex);
		//buffer_peticiones_encolar(&buffer,&ult_peticion);
		//++pet_buffer;
		//sleep(5);
	}
	printf("finalizaciones encoladas\n");
	
	for (i=0;i<k;++i) 
	{
		printf("antes de join %ld\n", i);
		pthread_join(servidor[i],(void**)&exit_status);
		printf("join %ld\n", i);
		free(exit_status);
	}
	printf("servidores finalizados\n");
	
	pthread_mutex_destroy(&mutex);
	pthread_cond_destroy(&pet_ready);
	return 0;
}
