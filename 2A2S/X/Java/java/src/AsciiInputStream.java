import java.io.*;
import java.net.*;

public class AsciiInputStream extends FilterInputStream {
	
    public AsciiInputStream(InputStream inputStream) {
        super(inputStream);
    }

    public int read() throws IOException {
        int n = 0;
        // this.n.read? in = ?? no seria inputStream
        n = this.in.read(); 
        
        // 60 en ascii es <
        // 62 en acii es > 
        // 10 en ascii es \n
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
        
        return n;
    }
}