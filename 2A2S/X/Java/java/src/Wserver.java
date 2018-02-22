import java.net.*;
import java.io.*;


public class Wserver {
	
	public static void main (String[] args) {
		ServerSocket serverSocket;
		Socket socket2;
        
        Hilo hilo;
		try
		{
			serverSocket = new ServerSocket(9202); //9202 -> nuestro puerto
			while(true){
				socket2 = serverSocket.accept();
				hilo = new Hilo(socket2);
				
				hilo.start();	
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
