package firstmilestone;

/*
 * Imported necessary libraries.
 */
import java.util.Date;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Class to print time between dates and total time,
 * in a correct format.
 *
 * Singleton pattern.
 *
 * @author Ramon 1400214, David 1391968
 *
 */
@SuppressWarnings("serial")
public final class TimePrinter implements Serializable {

    /**
     * Seconds in minute for time conversion.
     */
    private static final long SECONDS_IN_MINUTE = 60;

    /**
     * Minutes in hour for time conversion.
     */
    private static final long MINUTES_IN_HOUR = 60;

    /**
     * Time conversion to convert milliseconds to seconds.
     */
    private static final long TIME_CONVERSION  = 1000;

    /**
     * Instance for singleton pattern.
     */
    private static TimePrinter instance = null;

    /**
     * TimePrinter constructor.
     */
    private TimePrinter() {
        //MyLogger.info("Creating new TimePrinter");
    }

    /**
     * Get instance for Singleton pattern.
     *
     * @return instance instance of the class
     */
    public static TimePrinter getInstance() {
        //MyLogger.info("getInstance of TimePrinter called");
        if (instance == null) {
            instance = new TimePrinter();
        }
        return instance;
    }

    /**
     * Gets the time between two dates as a String.
     *
     * @param initialDate starting date
     * @param finalDate ending date
     *
     * @return the time between those dates as a string
     *
     * @throws WrongParamsException precondition of params
     */
    public String printTimeBetweenTwoDates(
            final Date initialDate,
            final Date finalDate) throws WrongParamsException {
        if (initialDate == null
                || finalDate == null
                ) { // || (initialDate.getTime() - finalDate.getTime() > 0)
            throw new WrongParamsException("Wrong params");
        }
        //MyLogger.info("TimePrinter printing time between dates");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(initialDate);
        long initialHour = calendar.get(Calendar.HOUR);
        long initialMinutes = calendar.get(Calendar.MINUTE);
        long initialSeconds = calendar.get(Calendar.SECOND);

        calendar.setTime(finalDate);

        long endHour = calendar.get(Calendar.HOUR);
        long endMinutes = calendar.get(Calendar.MINUTE);
        long endSeconds = calendar.get(Calendar.SECOND);

        return (Math.abs(endHour - initialHour) + "h "
                + Math.abs(endMinutes - initialMinutes) + "m "
                + Math.abs(endSeconds - initialSeconds) + "s");
    }

    /**
     * Prints the total time in format Xh Ym Zs.
     *
     * @param totalTime total time to return
     *
     * @return String Xh Ym Zs
     *
     * @throws WrongParamsException precondition of params
     */
    public String printTotalTime(final long totalTime)
            throws WrongParamsException {
        if (totalTime < 0) {
            throw new WrongParamsException("Wrong params");
        }

        //MyLogger.info("TimePrinter printing total time");
        long hours   = 0;
        long minutes = 0;
        long seconds = totalTime / TIME_CONVERSION;

        while (seconds > SECONDS_IN_MINUTE) {
            seconds -= SECONDS_IN_MINUTE;
            minutes += 1;
        }

        while (minutes > MINUTES_IN_HOUR) {
            minutes -= MINUTES_IN_HOUR;
            hours   += 1;
        }

        return (hours + "h " + minutes + "m " + seconds + "s");
    }

    /**
     * Returns a string of a date in the correct format.
     *
     * @param dateToShow date to show correctly
     *
     * @return String date in correct format as string
     */
    public String printDate(final Date dateToShow) {
        //MyLogger.info("TimePrinter printDate called");
        if (dateToShow == null) {
            return "                     ";
        }
        return new SimpleDateFormat("dd.MM.yyyy '-' hh:mm:ss")
                .format(dateToShow);
    }
}
