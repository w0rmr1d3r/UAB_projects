/***************************************************************************
 *            fwServer.c
 *
 *  Copyright  2016  mc
 *  <mc@<host>>
 ****************************************************************************/

#include "fwServer.h"	

/**
 * Returns the port specified as an application parameter or the default port
 * if no port has been specified.
 * @param argc the number of the application arguments.
 * @param an array with all the application arguments.
 * @return  the port number from the command line or the default port if 
 * no port has been specified in the command line. Returns -1 if the application
 * has been called with the wrong parameters.
 */
int getPort(int argc, char* argv[])
{
  int param;
  int port = DEFAULT_PORT;

  optind=1;
  // We process the application execution parameters.
	while((param = getopt(argc, argv, "p:")) != -1){
		switch((char) param){
			case 'p':
			  // We modify the port variable just in case a port is passed as a 
			  // parameter
				port = atoi(optarg);
				break;
			default:
				printf("Parametre %c desconegut\n\n", (char) param);
				port = -1;
		}
	}
	
	return port;
}

  
 /**
 * Function that sends a HELLO_RP to the  client
 * @param sock the communications socket
 */
void process_HELLO_msg(int sock)
{
 	char buffer[MAX_BUFF_SIZE];
	struct hello_rp hello;

	hello.opcode = (unsigned short) MSG_HELLO_RP;
	strcpy(hello.msg, "Hello World");
	
	*((struct hello_rp *)(buffer)) = hello;
	size_t size = sizeof(buffer);

	send(sock, buffer, size, 0);
}


/**
 * Returns every rule from the list
 * @param sock socket where the connection has been established
 * @param *chain list of rules
 */
void process_list(int sock, struct FORWARD_chain *chain)
{
  	char buffer[MAX_BUFF_SIZE];
	memset(&buffer, '\0', sizeof(buffer));
	stshort(MSG_RULES, buffer);

	stshort(chain->num_rules, buffer+2);

	// bucle para meter las reglas en el buffer
	struct fw_rule * aux_pointer = chain->first_rule;
	rule aux_rule;

	int i;
	for(i=0; i<(chain->num_rules);i++) {
		aux_rule = aux_pointer->rule;

		aux_rule.src_dst_addr = htons(aux_rule.src_dst_addr);
		aux_rule.addr.s_addr = htonl(aux_rule.addr.s_addr);
		aux_rule.mask = htons(aux_rule.mask); 
		aux_rule.src_dst_port = htons(aux_rule.src_dst_port);
		aux_rule.port = htons(aux_rule.port); 

		*((rule*)(buffer+4)+i) = aux_rule;
		aux_pointer = aux_pointer->next_rule;
	}	

	size_t size = sizeof(buffer);
	send(sock, buffer, size, 0);
}


/**
 * Adds a new rule to the list of rules
 * @param sock socket where the connection has been established
 * @param *chain list of rules
 * @param buffer buffer where the new data for the new rule is
 */
void process_add(int sock, struct FORWARD_chain *chain, char* buffer)
{
	rule new_rule;
	new_rule = *((rule *)(buffer+2));
	
	new_rule.src_dst_addr = ntohs(new_rule.src_dst_addr);
	new_rule.addr.s_addr = ntohl(new_rule.addr.s_addr);
	new_rule.mask = ntohs(new_rule.mask); 
	new_rule.src_dst_port = ntohs(new_rule.src_dst_port);
	new_rule.port = ntohs(new_rule.port); 

	struct fw_rule * new_fw_rule;
	new_fw_rule = malloc(sizeof(struct fw_rule));
	new_fw_rule->rule = new_rule;
	new_fw_rule->next_rule = NULL;
	
	if(chain->num_rules == 0) {
		chain->first_rule = new_fw_rule;
	}
	else {
		struct fw_rule * aux_pointer;
		aux_pointer = chain->first_rule;

		while(aux_pointer->next_rule != NULL) {
			aux_pointer = aux_pointer->next_rule;
		}
		aux_pointer->next_rule = new_fw_rule;
	}

	chain->num_rules++;
	
	char bufferok[MAX_BUFF_SIZE];
	size_t size = sizeof(bufferok);
	memset(&bufferok, '\0', size);
	stshort(MSG_OK, bufferok);
	send(sock, bufferok, size, 0);
}


/**
 * Changes a rule selected by the user with the new
 * parameters extracted from the buffer
 * @param sock socket where the connection has been established
 * @param *chain list of rules
 * @param buffer buffer where the new data is
 */
void process_change(int sock, struct FORWARD_chain *chain, char* buffer)
{
	unsigned short number;
	number = ldshort(buffer+2);

	if( ((int)number < (chain->num_rules-1)) && ((int)number > 0) && ((chain->num_rules) > 0)) {
		rule new_rule;
		new_rule = *((rule *)(buffer+4));
	
		new_rule.src_dst_addr = ntohs(new_rule.src_dst_addr);
		new_rule.addr.s_addr = ntohl(new_rule.addr.s_addr);
		new_rule.mask = ntohs(new_rule.mask); 
		new_rule.src_dst_port = ntohs(new_rule.src_dst_port);
		new_rule.port = ntohs(new_rule.port);
	
		struct fw_rule * aux_pointer;
	
		aux_pointer = chain->first_rule;
	
		if(chain->num_rules == 1 || number == 1) {
			aux_pointer->rule = new_rule;
		}
		else {
			int i = 0;
			while (i < ((int)number-1)) {
				aux_pointer = aux_pointer->next_rule;
				i++;
			}
			aux_pointer->rule = new_rule;
		}
	
		char bufferok[MAX_BUFF_SIZE];
		size_t size = sizeof(bufferok);
		memset(&bufferok, '\0', sizeof(bufferok));
		stshort(MSG_OK, &bufferok);
		send(sock, bufferok, size, 0);
	}
	else {
		char bufferok[MAX_BUFF_SIZE];
		size_t size = sizeof(bufferok);
		memset(&bufferok, '\0', sizeof(bufferok));
		stshort(MSG_ERR, &bufferok);
		send(sock, bufferok, size, 0);
	}


}


/**
 * Deletes the selected by the user rule
 * @param sock socket where the connection has been established
 * @param chain list of rules
 * @param buffer buffer from where we receive the data
 */
void process_delete(int sock, struct FORWARD_chain *chain, char* buffer)
{
	unsigned short number;
	number = ldshort(buffer + 2);

	if( ((int)number < (chain->num_rules-1)) && ((int)number > 0) && ((chain->num_rules) > 0)) {
		struct fw_rule * aux_pointer_old;
		struct fw_rule * aux_pointer;
	
		aux_pointer = chain->first_rule;
	
		if(chain->num_rules == 1) {
			free(aux_pointer);
		}
		else {
			if(number == 1) {
				aux_pointer_old = aux_pointer->next_rule;
				free(aux_pointer);
				chain->first_rule = aux_pointer_old;
			}
			else {
				int i = 0;
				while (i < ((int)number-2)) {
					aux_pointer = aux_pointer->next_rule;
					i++;
				}
				aux_pointer_old = aux_pointer->next_rule;
				aux_pointer->next_rule = aux_pointer_old->next_rule;
				free(aux_pointer_old);
			}
		}
	
		chain->num_rules--;
		
		char bufferok[MAX_BUFF_SIZE];
		size_t size = sizeof(bufferok);
		memset(&bufferok, '\0', sizeof(bufferok));
		stshort(MSG_OK, &bufferok);
		send(sock, bufferok, size, 0);
	}
	else {
		char bufferok[MAX_BUFF_SIZE];
		size_t size = sizeof(bufferok);
		memset(&bufferok, '\0', sizeof(bufferok));
		stshort(MSG_ERR, &bufferok);
		send(sock, bufferok, size, 0);
	}
}


/**
 * Deletes all the rules inside the chain
 * @param sock socket where the connection has been established
 * @param chain list of rules
 */
void flush_list(int sock, struct FORWARD_chain *chain)
{
	struct fw_rule * aux_pointer_next = chain->first_rule;
	struct fw_rule * aux_pointer = chain->first_rule;

	while (aux_pointer != NULL) {
		aux_pointer_next = aux_pointer->next_rule;
		free(aux_pointer);
		aux_pointer = aux_pointer_next;
	}
	
	chain->num_rules = 0;
	chain->first_rule = NULL;

	char bufferok[MAX_BUFF_SIZE];
	size_t size = sizeof(bufferok);
	memset(&bufferok, '\0', sizeof(bufferok));
	stshort(MSG_OK, &bufferok);
	send(sock, bufferok, size, 0);
}

 
 /** 
 * Receives and process the request from a client.
 * @param the socket connected to the client.
 * @param chain the chain with the filter rules.
 * @return 1 if the user has exit the client application therefore the 
 * connection whith the client has to be closed. 0 if the user is still 
 * interacting with the client application.
 */
int process_msg(int sock, struct FORWARD_chain *chain)
{
	unsigned short op_code;
 	int finish = 0;
	char buffer[MAX_BUFF_SIZE];
	memset(&buffer, '\0', sizeof(buffer));
	size_t size = sizeof(buffer);
	recv(sock, buffer, size, 0);
	op_code = ldshort(buffer);
    
	switch(op_code){
		case MSG_HELLO:
			process_HELLO_msg(sock);
			break;    
		case MSG_LIST: 
			process_list(sock, chain);  
			break;
		case MSG_ADD:
			process_add(sock, chain, buffer);
			break;                              
		case MSG_CHANGE:     
			process_change(sock, chain, buffer);
			break;                              
		case MSG_DELETE:
			process_delete(sock, chain, buffer);
			break;                              
		case MSG_FLUSH:
			flush_list(sock, chain);
			break;                                                                                     
		case MSG_FINISH:
			finish = 1;
			break;
		default:
			perror("Message code does not exist.\n");
	} 
  
  return finish;
}


/**
 *
 */ 
int main(int argc, char *argv[]){
	int port = getPort(argc, argv);
	int finish=0;
	int s, s2, pid;

	struct sockaddr_in origen;
	origen.sin_port=ntohs(port);//9202
	origen.sin_family= AF_INET;
	origen.sin_addr.s_addr = INADDR_ANY;//la direccion: deic-dc10 transform to long
	
	struct sockaddr_in cliente;
	socklen_t cliente_addrlen = sizeof(cliente); 

 	struct FORWARD_chain chain;
   	chain.num_rules=0;
 	chain.first_rule=NULL;

	s=socket(PF_INET, SOCK_STREAM, IPPROTO_TCP);
	bind(s,(struct sockaddr*)&origen, sizeof(origen));
	listen(s, MAX_QUEUED_CON);

	while(1) {
		s2 = -1;
		s2 = accept(s, (struct sockaddr*)&cliente, &cliente_addrlen);
		
		if (s2 != -1) {
			pid = fork();
			if(pid!=-1) {
				if(pid==0) {
			    	do {
			      		finish = process_msg(s2, &chain);
			    	}while(finish!=1);
					exit(0);
				}
	    	}
	    }
	 }  
	return 0;
 }
