/***************************************************************************
 *            fwServer.h
 *
 *  Copyright  2016  mc
 *  <mcarmen@<host>>
 ****************************************************************************/
 #include "common.h"
 
 #define MAX_QUEUED_CON 10 // Max number of connections queued



/**
 * Structures to implement the firewall rules
 * ==========================================
 */

 struct fw_rule
 {
    rule rule;
    struct fw_rule * next_rule;
 };

 struct FORWARD_chain
 {
   int num_rules;
   struct fw_rule * first_rule;
 };

 
/**
 * Returns the port specified as an application parameter or the default port
 * if no port has been specified.
 * @param argc the number of the application arguments.
 * @param an array with all the application arguments.
 * @return  the port number from the command line or the default port if 
 * no port has been specified in the command line. Returns -1 if the application
 * has been called with the wrong parameters.
 */
int getPort(int argc, char* argv[]);

 
  /**
 * Function that sends a HELLO_RP to the  client
 * @param sock the communications socket
 */
void process_HELLO_msg(int sock);

 /** 
 * Receives and process the request from a client.
 * @param the socket connected to the client.
 * @param chain the chain with the filter rules.
 * @return 1 if the user has exit the client application therefore the 
 * connection whith the client has to be closed. 0 if the user is still 
 * interacting with the client application.
 */
int process_msg(int sock, struct FORWARD_chain *chain);

/**
 * Returns every rule from the list
 * @param sock socket where the connection has been established
 * @param *chain list of rules
 */
void process_list(int sock, struct FORWARD_chain *chain);

/**
 * Adds a new rule to the list of rules
 * @param sock socket where the connection has been established
 * @param *chain list of rules
 * @param buffer buffer where the new data for the new rule is
 */
void process_add(int sock, struct FORWARD_chain *chain, char* buffer);

/**
 * Changes a rule selected by the user with the new
 * parameters extracted from the buffer
 * @param sock socket where the connection has been established
 * @param *chain list of rules
 * @param buffer buffer where the new data is
 */
void process_change(int sock, struct FORWARD_chain *chain, char* buffer);

/**
 * Deletes the selected by the user rule
 * @param sock socket where the connection has been established
 * @param chain list of rules
 * @param buffer buffer from where we receive the data
 */
void process_delete(int sock, struct FORWARD_chain *chain, char* buffer);

/**
 * Deletes all the rules inside the chain
 * @param sock socket where the connection has been established
 * @param chain list of rules
 */
void flush_list(int sock, struct FORWARD_chain *chain);

