package controller;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.Stateless;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


//TODO
//TODO explicitly annotate serverEndpoint as an 
@Stateless
@ServerEndpoint(value="/WeatherForecast/") 
public class ServerEndPoint {
	private static Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());

	/**
	 * Method that sends a message to all the Sessions in the clients set. 
	 * @param msg The message to be sent.
	 */
	public void triggerAlert(String msg) {
		
		System.out.println("Received: " + msg); //DEBUG		
		//TODO
		for (Session session : clients) {
			try {
				session.getBasicRemote().sendObject(msg);
			} catch (IOException | EncodeException e) {
				//e.printStackTrace();
				System.out.println("ERROR >>> ServerEndPoint triggerAlert");
				System.out.println("ERROR >>> Enviar mensaje a los clientes");
			}
		}
	}

	@OnOpen
	public void open(Session client) {
		System.out.println("On open: " + client.getId()); //DEBUG
		//TODO
		clients.add(client);
	}

	@OnClose
	public void close(Session client) {
		System.out.println("On close : " + client.getId()); //DEBUG
		//TODO
		clients.remove(client);	
	}
}

