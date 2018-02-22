

import java.io.*;
import java.net.*;

public class AsciiInputStream extends FilterInputStream {
	
    public AsciiInputStream(InputStream inputStream) {
        super(inputStream);
    }

    public int read() throws IOException {
        int n = 0;
        n = this.in.read(); 
        
        // 60 en ascii es <
        // 62 en acii es > 
        // 10 es \n en ascci (salto de linea)
        do {
        	if (n == 60) {
            	while (n != 62) {
                    n = this.in.read();
                }
            	if(n == 62) {
            		n = 10;
            	}
            	else {
            		n = this.in.read();
            	} 
            }
        } while (n == 60);
        
        //primer caracter entre tags
        return n;
    }
    
    public byte[] clean() throws IOException {
    	int n;
    	int i = in.available();
    	
    	byte[] auxbytearray = new byte[i];
    	
    	i = 0;
    	n = read();
    	while (n != -1) {
    		auxbytearray[i] = (byte)n;
    		n = read();
    		i++;
    	}
    	
    	return auxbytearray;
    }
}