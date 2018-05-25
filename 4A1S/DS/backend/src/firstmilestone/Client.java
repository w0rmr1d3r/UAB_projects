package firstmilestone;

/**
 * Has every project and task, initiates the clock
 * and saves and loads the project root.
 * Generates reports as well
 *
 * @author Ramon 1400214, David 1391968
 *
 */
public class Client {

    static {
        // Run -> Run Configurations -> Arguments -> VM... ->  type "-ea"
        // This way asserts may be compiled
        boolean assertsEnabled = false;
        assert assertsEnabled = true; // Intentional side effect
        if (!assertsEnabled) {
            throw new RuntimeException("Asserts must be enabled!!!");
        }
    }

    /**
     * MAIN.
     *
     * @param args it takes no console parameters
     *
     * @throws InterruptedException throws only if necessary
     *
     * @throws WrongParamsException precondition of params
     */
    @SuppressWarnings("static-access")
    public static void main(final String[] args)
            throws InterruptedException, WrongParamsException {

        MyLogger.info("Minimum and Refresh time set");
        long minimumTime = 1;
        long refreshTime = 1;

        MyLogger.info("Creating and starting Clock");
        Clock clock = Clock.getInstance(refreshTime);
        Thread clockThread = new Thread(clock);
        assert clock.invariant();

        MyLogger.info("Starting clock");
        clockThread.start();

        MyLogger.info("Starting tests");
        //Tester.getInstance(minimumTime, refreshTime).testA1();
        //Tester.getInstance(minimumTime, refreshTime).testA2();
        //Tester.getInstance(minimumTime, refreshTime).testShortReport();
        Tester.getInstance(minimumTime, refreshTime).testDetailedReport();

        MyLogger.info("Stopping clock");
        clock.stop();
    }
}
