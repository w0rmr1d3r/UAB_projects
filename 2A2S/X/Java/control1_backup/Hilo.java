

import java.net.*;
import java.io.*;

public class Hilo extends Thread {
		
	// atributos de la clase
	private Socket socket2;
	private InputStream is;
	private OutputStream os;
	private BufferedReader reader;
	private BufferedOutputStream writer;  
	private File filetoclient;
    
    // Constructor
    public Hilo(Socket socket2){
		this.socket2 = socket2;	
    }
     
    // filters the client petition
	// returns the file name, filetype and filter    
    private static String[] filterpetition(String clientpetition){
		
		String[] parts = clientpetition.substring(clientpetition.indexOf("/") + 1).split(" ");
		String filetoshow = parts[0];
		String filetype = filetoshow.substring(filetoshow.indexOf(".") + 1);
		String asciitrue, ziptrue, gziptrue;
		
		boolean ascii, zip, gzip;
		ascii = zip = gzip = false;
		
		int encontrado = parts[0].indexOf('?');
		
		// hay specs
		if (encontrado >= 0) {
			filetoshow = parts[0].substring(0, encontrado);
			if(filetype.contains("asc=true")) {
				ascii = true;
			}
			if(filetype.contains("&zip=true") || filetype.contains("?zip=true")) {
				zip = true;
			}
			if(filetype.contains("gzip=true")) {
				gzip = true;
			}
			filetype = filetype.substring(0, filetype.indexOf("?"));
		}
		asciitrue = Boolean.toString(ascii);
		ziptrue = Boolean.toString(zip);
		gziptrue = Boolean.toString(gzip);

		return new String[]{filetoshow, filetype, asciitrue, ziptrue, gziptrue};
	}
    
    // writes header to client
    private static void WriteHeader(BufferedOutputStream miwriter, String clientheader) throws IOException {
    	byte[] byteheader = new byte [(int)clientheader.length()];
		byteheader = clientheader.getBytes();
		miwriter.write(byteheader);
    }
   
    // Writes files to client
    private static void WriteFile(FileInputStream fis, BufferedOutputStream miwriter, File filetoclient) throws IOException {
    	BufferedInputStream bis = new BufferedInputStream(fis);
		byte [] mybytearray  = new byte [(int)filetoclient.length()];
		bis.read(mybytearray,0,mybytearray.length);
		miwriter.write(mybytearray, 0, mybytearray.length);
    }
    
    // ASCII FILTER
    private static void AsciiFilter(String headertoclient, String filename, FileInputStream fis, BufferedOutputStream miwriter, File filetoclient) throws IOException {
    	// cabecera ASCII
		headertoclient = headertoclient.substring(0, headertoclient.length()-2);
		headertoclient = headertoclient.concat("Content-Disposition: attachment; filename=");
		headertoclient = headertoclient.concat(filename).concat(".asc\n\n");
		
		// escribimos cabecera
		WriteHeader(miwriter, headertoclient);
		
		// ASCII
		AsciiInputStream miascii = new AsciiInputStream(fis);
		byte [] asciibytearray  = new byte [(int)filetoclient.length()];
		asciibytearray = miascii.clean();
		miwriter.write(asciibytearray, 0, asciibytearray.length);
        // modificar el writer
    }
    
    // Run
    public void run(){
		try{
			this.is = socket2.getInputStream();
			this.os = socket2.getOutputStream();
			this.reader = new BufferedReader(new InputStreamReader(is)); 
			this.writer = new BufferedOutputStream(new BufferedOutputStream(os));
			
			// FILTER client input
			String[] filteredfile = filterpetition(reader.readLine()); 	
			String headertoclient = "HTTP/1.1 200 OK\nContent-Type: image/jpeg\n\n";

			filetoclient = new File(filteredfile[0]);
			
			if(filetoclient.exists()){	
				
				FileInputStream fis = new FileInputStream(filetoclient);
				
				// filtro de cabecera
				switch (filteredfile[1]) {
					case "html":
						headertoclient = "HTTP/1.1 200 OK\nContent-Type: text/html\n\n";
						if (filteredfile[2] == "true") {
							AsciiFilter(headertoclient, filteredfile[0], fis, writer, filetoclient);
						}
						 break;
					case "gif":
						headertoclient = "HTTP/1.1 200 OK\nContent-Type: image/gif\n\n";
						break;
					case "jpeg":
						headertoclient = "HTTP/1.1 200 OK\nContent-Type: image/jpeg\n\n";
						break;
					case "png":
						headertoclient = "HTTP/1.1 200 OK\nContent-Type: image/png\n\n";
						break;
					default:
						headertoclient = "HTTP/1.1 200 OK\nContent-Type: text/plain\n\n";
						 break;
				}

				// envio cabecera
				WriteHeader(writer, headertoclient);
				
				// escribimos fichero en cliente pedido sin parametros
				WriteFile(fis, writer, filetoclient);
			}
			else {
				// 404 not found
				filetoclient = new File("not_found.jpg");
				WriteHeader(writer, headertoclient);
				FileInputStream fis = new FileInputStream(filetoclient);
				WriteFile(fis, writer, filetoclient);
			}
			socket2.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
    } 
}
