#include <stdio.h> // scanf, printf
#include <stdlib.h> // free(exit_status)
#include <pthread.h> // pthread_t, pthread_cond_t, ...

#define TAM_BUFFER 5
// true 1
// false 0

pthread_mutex_t mutex;
pthread_cond_t pet_ready;

int pet_esp = 0;

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
	
	int i;
	for(i = 1;i<=100;)
	{
		mi_peticion.id_cliente = mi_id;
		mi_peticion.id_compra = i;
		
		pthread_mutex_lock(&mutex);
		if(buffer_peticiones_lleno(&buffer) == 0)
		{
			buffer_peticiones_encolar(&buffer,&mi_peticion);
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
		pthread_mutex_lock(&mutex);
		if(buffer_peticiones_vacio(&buffer) == 0)
		{
			buffer_peticiones_atender(&buffer,&mi_peticion);
			if(pet_esp > 0)
			{
				pthread_cond_signal(&pet_ready);
			}
			if(mi_peticion.id_cliente == 0 && mi_peticion.id_compra == 0)
			{
				break;
			}
			fprintf(file, "Cliente-%d %d\n", mi_peticion.id_cliente, mi_peticion.id_compra);
		}
		pthread_mutex_unlock(&mutex);
	}
	
	fclose(file);

	pthread_exit(0);
}

int main(int argc,char** argv) 
{
	pthread_mutex_init(&mutex, NULL);
	buffer_peticiones_inicializar(&buffer);
	peticion_t ult_peticion;
	ult_peticion.id_cliente= 0;
	ult_peticion.id_compra = 0;
	
	long int j;
	printf("Inserte numero de clientes:\n");
	scanf("%ld", &j);
	
	pthread_t thread[j+1];
	
	long int i = 0;
	for(i = 0; i<j;i++)
	{
		pthread_create(thread+i,NULL,(void* (*)(void*))producer,(void*)(i));
	}
	
	pthread_create(thread+j,NULL,(void* (*)(void*))consumer,(void*)(j));

	int* exit_status;
	for (i=0;i<j;++i) 
	{
		pthread_join(thread[i],(void**)&exit_status);
		free(exit_status);
	}
	
	buffer_peticiones_encolar(&buffer,&ult_peticion);
	pthread_join(thread[j],(void**)&exit_status);
	
	return 0;
}
