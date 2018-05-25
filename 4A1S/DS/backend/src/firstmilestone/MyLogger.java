package firstmilestone;

/*
 * Imported necessary libraries
 */
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class MyLogger
 * Used to log information into the console and,
 * not to use an attribute for each class.
 *
 * @author Ramon 1400214, David 1391968
 *
 */
public final class MyLogger {

    /**
     * Logger to use to log actions.
     * Only 1 logger in the project.
     */
     private static final Logger LOGGER = Logger.getLogger("My Logger");

    /**
     * Logs information.
     *
     * @param message message to log
     */
    public static void info(final String message) {
        LOGGER.log(Level.INFO, message);
    }

    /**
     * Logs SEVERE information.
     *
     * @param message message to log
     */
    public static void severe(final String message) {
        LOGGER.log(Level.SEVERE, message);
    }

    /**
     * Logs warnings.
     *
     * @param message message to log
     */
    public static void warning(final String message) {
        LOGGER.log(Level.WARNING, message);
    }
}
