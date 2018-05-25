package firstmilestone;

/**
 * Exception for wrong params in pre-conditions.
 *
 * @author Ramon 1400214, David 1391968
 *
 */
@SuppressWarnings("serial")
public class WrongParamsException extends Exception {

    /**
     * Creates the exception.
     */
    public WrongParamsException() {
        super();
    }

    /**
     * Creates the exception.
     *
     * @param message message to say to the user
     */
    public WrongParamsException(final String message) {
        super(message);
    }
}
