import java.net.*;
import java.io.*;
import java.util.zip.*;


public class Hilo extends Thread {
		
	// atributos de la clase
	private Socket socket2;
	private InputStream is;
	private OutputStream os;
	private File filetoclient;
    
    // Constructor
    public Hilo(Socket socket2){
		this.socket2 = socket2;	
    }
     
    // filters the client petition   
    private static String[] filterpetition(String clientpetition){
		String[] parts = clientpetition.substring(clientpetition.indexOf("/") + 1).split(" ");
		String filetoshow = parts[0];
		String filetype = filetoshow.substring(filetoshow.indexOf(".") + 1);
		String asciitrue, ziptrue, gziptrue;
		
		boolean ascii, zip, gzip;
		ascii = zip = gzip = false;
		
		int encontrado = parts[0].indexOf('?');
	
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

    // Run
    @SuppressWarnings("deprecation")
	public void run(){
		try{
			this.is = socket2.getInputStream();
			this.os = socket2.getOutputStream();
			
			// FILTER client input
			String[] filteredfile = filterpetition(new BufferedReader(new InputStreamReader(is)).readLine());
			String headertoclient = "HTTP/1.1 200 OK\nContent-Type: image/jpeg\n\n";
			
			filetoclient = new File(filteredfile[0]);
			
			if(filetoclient.exists()){	
				
				is = new FileInputStream(filetoclient);
				
				// filtro de cabecera estandar
				// faltan algunas cabeceras
				switch (filteredfile[1]) {
					case "html":
// hacer que asc solo para html
						headertoclient = "HTTP/1.1 200 OK\nContent-Type: text/html\n\n";
						if(filteredfile[2] == "true" && filteredfile[3] == "false" && filteredfile[4] == "false"){
						// solo ascii
						filteredfile[0] = filteredfile[0].concat(".asc");
						headertoclient = headertoclient.substring(0, headertoclient.length()-2);
						headertoclient = headertoclient.concat("Content-Disposition: attachment; filename=").concat(filteredfile[0]).concat("\n\n");	
						}
						else if(filteredfile[2] == "true" && filteredfile[3] == "true" && filteredfile[4] == "false"){
						// ascii + zip
						filteredfile[0] = filteredfile[0].concat(".asc.zip");
						headertoclient = headertoclient.substring(0, headertoclient.length()-2);
						headertoclient = headertoclient.concat("\nContent-Disposition: attachment; filename=").concat(filteredfile[0]).concat("\n\n");
						}
						else if(filteredfile[2] == "true" && filteredfile[3] == "true" && filteredfile[4] == "true" && filteredfile[1] == "html"){
						// ascii + zip + gzip
						}
// ascii
				if(filteredfile[2] == "true"){
					is = new AsciiInputStream(is);
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
					case "txt":
						headertoclient = "HTTP/1.1 200 OK\nContent-Type: text/plain\n\n";
						break;
					default:
						// falta modificar y añadir nuevos
						headertoclient = "HTTP/1.1 200 OK\nContent-Type: text/plain\n\n";
						 break;
				}
				
				// seleccion header con parametros
				if(filteredfile[2] == "false" && filteredfile[3] == "true" && filteredfile[4] == "false"){
					// solo zip
					//filteredfile[0] = filteredfile[0].concat(".zip");
					headertoclient = "HTTP/1.1 200 OK\nContent-Type: application/zip\n";
					headertoclient = headertoclient.concat("Content-Disposition: filename=").concat(filteredfile[0]).concat(".zip\n\n");
				}
				else if(filteredfile[2] == "false" && filteredfile[3] == "false" && filteredfile[4] == "true"){
					// solo gzip
					filteredfile[0] = filteredfile[0].concat(".gz");
					headertoclient = "HTTP/1.1 200 OK\nContent-Type: application/x-gzip\n";
					headertoclient = headertoclient.concat("Content-Disposition: filename=").concat(filteredfile[0]).concat("\n\n");
				
				}
				else if(filteredfile[2] == "false" && filteredfile[3] == "true" && filteredfile[4] == "true"){
					// zip + gzip
					//filteredfile[0] = filteredfile[0].concat(".zip.gz");
					headertoclient = "HTTP/1.1 200 OK\nContent-Type: application/x-gzip";
					headertoclient = headertoclient.concat("\nContent-Disposition: attachment; filename=").concat(filteredfile[0]).concat(".zip.gz\n\n");
				}

				int i = 0;
				while(i < headertoclient.length()){
					os.write((int) headertoclient.charAt(i));
					i++;
				}
				
				
				// zip + gzip
				if(filteredfile[3] == "true" && filteredfile[4] == "true"){
					// aqui debe haber un error que no vemos
					// o no sabemos solucionar
					System.out.println(headertoclient);
					os = new GZIPOutputStream(os);
					os = new ZipOutputStream(os);
					((ZipOutputStream) os).putNextEntry(new ZipEntry(filteredfile[0]));
					
				}
				else{
					// zip
					if(filteredfile[3] == "true"){
						os = new ZipOutputStream(os);
						((ZipOutputStream) os).putNextEntry(new ZipEntry(filteredfile[0]));
					}
					// gzip
					if (filteredfile[4] == "true"){
						os = new GZIPOutputStream(os);
					}
				}
				

				// envio de fichero
				int c;
				while((c = is.read()) != -1){
					os.write(c);
				}
				is.close();
				os.close();
				
				/*// cierre de zip
				if(filteredfile[3] == "true" && filteredfile[4] == "false"){
					((ZipOutputStream) os).closeEntry();
					((ZipOutputStream) os).close();
				}
				// cierre gzip
				if(filteredfile[3] == "false" && filteredfile[4] == "true"){
					((GZIPOutputStream) os).finish();
				}
				// cierre de zip + gzip
				if(filteredfile[3] == "true" && filteredfile[4] == "true"){
					// error de casteo aqui
					// os es GZIP y no ZIP
					
					//os.close();
					/*((GZIPOutputStream) os).finish();
					((ZipOutputStream) os).closeEntry();
					((ZipOutputStream) os).close();
				}*/
				
			}
			else {
				// 404 not found
				filetoclient = new File("not_found.jpg");
				is = new FileInputStream(filetoclient);
				// envia header
				int i = 0;
				while(i < headertoclient.length()){
					os.write((int) headertoclient.charAt(i));
					i++;
				}
				// enviar fichero por os
				int c;
				while((c = is.read()) != -1){
					os.write(c);
				}
			}
			socket2.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
    } 
}
