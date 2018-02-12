package exceptions;

import java.lang.Exception;

/**
 * Class MyException
 * @author ramonguimera, davidcuadrado
 *
 */
@SuppressWarnings("serial")
public class MyException extends Exception {

    /**
     * Constructor
     */
    public MyException() {
        super();
    }
    
    /**
     * Constructor
     * @param message message to log
     */
    public MyException(final String message) {
        super(message);
    }
}
