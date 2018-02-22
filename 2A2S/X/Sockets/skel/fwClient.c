/***************************************************************************
 *            fwClient.h
 *
 *  Copyright  2016  mc
 *  <mcarmen@<host>>
 ****************************************************************************/
#include "fwClient.h"


/**
 * Function that sets the field addr->sin_addr.s_addr from a host name 
 * address.
 * @param addr struct where to set the address.
 * @param host the host name to be converted
 * @return -1 if there has been a problem during the conversion process.
 */
int setaddrbyname(struct sockaddr_in *addr, char *host)
{
  struct addrinfo hints, *res;
	int status;

  memset(&hints, 0, sizeof(struct addrinfo));
  hints.ai_family = AF_INET;
  hints.ai_socktype = SOCK_STREAM; 
 
  if ((status = getaddrinfo(host, NULL, &hints, &res)) != 0) {
    fprintf(stderr, "getaddrinfo: %s\n", gai_strerror(status));
    return -1;
  }
  
  addr->sin_addr.s_addr = ((struct sockaddr_in *)res->ai_addr)->sin_addr.s_addr;
  
  freeaddrinfo(res);
    
  return 0;  
}


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
	while((param = getopt(argc, argv, "h:p:")) != -1){
		switch((char) param){
		  case 'h': break;
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
 * Returns the host name where the server is running.
 * @param argc the number of the application arguments.
 * @param an array with all the application arguments.
 * @Return Returns the host name where the server is running.<br />
 * Returns null if the application has been called with the wrong parameters.
 */
 char * getHost(int argc, char* argv[]){
  char * hostName = NULL;
  int param;
  
  optind=1;
    // We process the application execution parameters.
	while((param = getopt(argc, argv, "h:p:")) != -1){
		switch((char) param){
			case 'p': break;
			case 'h':
        hostName = (char*) malloc(sizeof(char)*strlen(optarg)+1);
				// Un cop creat l'espai, podem copiar la cadena
				strcpy(hostName, optarg);
				break;
			default:
				printf("Parametre %c desconegut\n\n", (char) param);
				hostName = NULL;
		}
	}
	
	printf("in getHost host: %s\n", hostName); //!!!!!!!!!!!!!!
	return hostName;
 }
 
  
/**
 * Shows the menu options. 
 */
void print_menu()
{
	printf("\nAplicació de gestió del firewall\n");
	printf("  0. Hello\n");
	printf("  1. Llistar les regles filtrat\n");
	printf("  2. Afegir una regla de filtrat\n");
	printf("  3. Modificar una regla de filtrat\n");
	printf("  4. Eliminar una regla de filtrat\n");
	printf("  5. Eliminar totes les regles de filtrat.\n");
	printf("  6. Sortir\n\n");
	printf("Escull una opcio: ");
} 

/**
 * Returns a rule entered from the keyboard.
 * @Return Returns the new rule.
 */
rule get_rule()
{
	char buffer_aux[MAX_BUFF_SIZE];
	char buffer_addr[MAX_BUFF_SIZE];
	char buffer_sd_addr[MAX_BUFF_SIZE];
	int mask, sd_port, port;
	rule new_rule;
	
	memset(&buffer_aux, '\0', sizeof(buffer_aux));
	
	printf("Introdueix la regla seguint el format:\n[src|dst] address netmask [sport|dport] [port]\n\n");
	scanf("%s %s %d %d %d", buffer_sd_addr, buffer_addr, &mask, &sd_port, &port);
	
	if(0 == strcmp(buffer_sd_addr, "src")) {
		new_rule.src_dst_addr = htons((unsigned short)SRC); 
	}
	else {
		if(0 == strcmp(buffer_sd_addr, "dst")) {
			new_rule.src_dst_addr = htons((unsigned short)DST); 
		}
		else {
			printf("error input [src|dst]\n");
		}	
	}
	
	struct in_addr addr;
	inet_aton(buffer_addr, &addr);
	new_rule.addr = addr;
	new_rule.mask = htons((unsigned short)mask); 
	new_rule.src_dst_port = htons((unsigned short)sd_port);
	new_rule.port = htons((unsigned short)port); 
 
	return new_rule;
}


/**
 * Sends a HELLO message and prints the server response.
 * @param sock socket used for the communication.
 */
void process_hello_operation(int sock)
{
  	char buffer[MAX_BUFF_SIZE];
	memset(&buffer, '\0', sizeof(buffer));
	stshort(MSG_HELLO, buffer);
	size_t size = sizeof(buffer);

	if(send(sock, buffer, size, 0) == -1)
		printf("Error\n");

	recv(sock, buffer, size, 0);
	printf("\n%s\n", buffer+2);
}


/**
 * Asks for the list of rules entered and it prints it.
 * @param sock socket used for the communication.
 */
void process_list(int sock)
{
  	char buffer[MAX_BUFF_SIZE];
	memset(&buffer, '\0', sizeof(buffer));
	stshort(MSG_LIST, buffer);
	size_t size = sizeof(buffer);

	if(send(sock, buffer, size, 0) == -1)
		printf("Send Error\n");
	
	recv(sock, buffer, size, 0);
	
	unsigned short number;
	number = ldshort(buffer+2);
	
	printf("\nFirewall FORWARD rules:\n------------------------\n");
	printf("number of rules: %hu\n", number);
	
	char buffer_sd_addr[3];
	char buffer_addr[16];
	char buffer_sd_port[5];
	unsigned short sd_addr, mask, sd_port, port;
	rule next_rule;
	
	int i;
	for(i = 0; i < number; i++) {
		next_rule = *((rule *)(buffer+4)+i);
		
		sd_addr = ntohs(next_rule.src_dst_addr);
		strcpy(buffer_addr, inet_ntoa(next_rule.addr));
		mask = ntohs(next_rule.mask);
		sd_port = ntohs(next_rule.src_dst_port);
		port = ntohs(next_rule.port);
		
		if(sd_addr == SRC)
			strcpy(buffer_sd_addr, "src");
		else
			strcpy(buffer_sd_addr, "dst");
		
		if(sd_port == SRC_P)
			strcpy(buffer_sd_port, "sport");
		else 
			strcpy(buffer_sd_port, "dport");
		
		printf("%d- %s %s/%hu %s %hu\n", i+1, buffer_sd_addr, buffer_addr, mask, buffer_sd_port, port);
	}
}


/**
 * Sends a new rule to be added to the list of rules
 * inside the server rule chain.
 * @param sock socket where the message is going to be sent from.
 */
void add_rule(int sock)
{
	char buffer[MAX_BUFF_SIZE];
	unsigned short op_code;
	memset(&buffer, '\0', sizeof(buffer));
	rule new_rule = get_rule(); 
	size_t size = sizeof(buffer);

	stshort(MSG_ADD, buffer);
	*((rule *)(buffer+2)) = new_rule;
	
	if(send(sock, buffer, size, 0) == -1)
		printf("Error\n");

	recv(sock, buffer, size, 0);
	op_code = ldshort(buffer);
	if(op_code != MSG_OK)
		printf("\nError al server\n");
	else
		printf("\nRegla insertada correctament\n");
}


/**
 * Selects one rule from the list of rules and
 * it is shanged for the new one entered.
 * @param sock socket where the message is going to be sent from
 */
void change_rule(int sock)
{
	char buffer[MAX_BUFF_SIZE];
	unsigned short op_code;
	memset(&buffer, '\0', sizeof(buffer));
	size_t size = sizeof(buffer);
	int regla;
	rule new_rule;

	printf("\nIntrodueix la regla que vols cambiar:");
	scanf("%d", &regla);

	new_rule = get_rule();
		
	stshort(MSG_CHANGE, buffer);
	stshort(regla, buffer+2);

	*((rule *)(buffer+4)) = new_rule;
		
	if(send(sock, buffer, size, 0) == -1)
		printf("Error\n");
	
	recv(sock, buffer, size, 0);
	op_code = ldshort(buffer);
	if(op_code!=MSG_OK)
		printf("\nError al server\n");
	else
		printf("\nCanvi correcte\n");
}


/**
 * Deletes the selected rule from the list of rules.
 * @param sock socket where the connection is established
 */
void delete_rule(int sock)
{
	char buffer[MAX_BUFF_SIZE];
	int regla;
	unsigned short op_code;
	size_t size = sizeof(buffer);
	
	memset(&buffer, '\0', sizeof(buffer));
	printf("\nIntrodueix la regla que vols eliminar:");
	scanf("%d", &regla);
	
	stshort(MSG_DELETE, buffer);
	stshort(regla, buffer+2);

	send(sock, buffer, size, 0);
	
	recv(sock, buffer, size, 0);
	op_code = ldshort(buffer);
	if(op_code!=MSG_OK)
		printf("\nError al server\n");
	else
		printf("\nDelete correcte\n");
}


/**
 * Flushes the entire list of rules
 * @param sock socket where the connection has been established
 */
void flush(int sock)
{
	char buffer[MAX_BUFF_SIZE];
	memset(&buffer, '\0', sizeof(buffer));
	stshort(MSG_FLUSH, buffer);
	size_t size = sizeof(buffer);
	unsigned short op_code;

	send(sock, buffer, size, 0);
	
	recv(sock, buffer, size, 0);
	op_code = ldshort(buffer);
	if(op_code!=MSG_OK)
		printf("\nError al server\n");
	else
		printf("\nflush correcte\n");
}


/**
 * Closes the socket connected to the server and finishes the program.
 * @param sock socket used for the communication.
 */
void process_exit_operation(int sock)
{
  	char buffer[MAX_BUFF_SIZE];
	memset(&buffer, '\0', sizeof(buffer));
	stshort(MSG_FINISH, buffer);
	size_t size = sizeof(buffer);
	send(sock, buffer, size, 0);
}


/** 
 * Function that process the menu option set by the user by calling 
 * the function related to the menu option.
 * @param s The communications socket
 * @param option the menu option specified by the user.
 */
void process_menu_option(int s, int option)
{		  
	switch(option) {
		case MENU_OP_HELLO:
			process_hello_operation(s);
			break;
		case MENU_OP_LIST_RULES: 
			process_list(s);     
			break;  
		case MENU_OP_ADD_RULE:
			add_rule(s);   
			break;   
		case MENU_OP_CHANGE_RULE:
			change_rule(s);
			break;   
		case MENU_OP_DEL_RULE:   
			delete_rule(s); 	
			break;
		case MENU_OP_FLUSH:
			flush(s);
			break;       
		case MENU_OP_EXIT:
			process_exit_operation(s);
			break;   
		default:
			printf("Invalid menu option\n");          
	}
}


/**
 *
 */
int main(int argc, char *argv[]){ 
 	int s;
  	unsigned short port;
  	char *hostName;  
  	int menu_option = 0;
	struct sockaddr_in origen;
	
	origen.sin_addr.s_addr = INADDR_ANY;//la direccion: deic-dc10 transform to long

  	port = getPort(argc, argv);  
	hostName = getHost(argc, argv);

	s=socket(PF_INET, SOCK_STREAM, IPPROTO_TCP);

	origen.sin_port = htons(port);//9202
	origen.sin_family= AF_INET;
	setaddrbyname(&origen, hostName);
	socklen_t size = sizeof(origen);
	
	connect(s,(struct sockaddr*)&origen, size);
 
  //Checking that the host name has been set.Otherwise the application is stopped. 
	if(hostName == NULL) {
		perror("No s'ha especificat el nom del servidor\n\n");
		return -1;
	}
    
   	do {
      	print_menu();
		// getting the user input.
		scanf("%d",&menu_option);
		printf("\n\n"); 
		process_menu_option(s, menu_option);

	}while(menu_option != MENU_OP_EXIT); //end while(opcio)
 
  return 0; 
}
