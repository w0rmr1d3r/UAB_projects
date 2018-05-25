package firstmilestone;

/*
 * Imported necessary libraries
 */
import java.util.Date;
import secondmilestone.DetailedReport;
import secondmilestone.Report;
import secondmilestone.ShortReport;

/**
 * Class to execute the tests for the project evaluation.
 *
 * Singleton pattern.
 *
 * @author Ramon 1400214, David 1391968
 *
 */
public final class Tester {

    /**
     * Represents 2 seconds in milliseconds.
     */
    private static final long TWO_SECONDS   = 2000;

    /**
     * Represents 3 seconds in milliseconds.
     */
    private static final long THREE_SECONDS = 3000;

    /**
     * Represents 4 seconds in milliseconds.
     */
    private static final long FOUR_SECONDS  = 4000;

    /**
     * Represents 6 seconds in milliseconds.
     */
    private static final long SIX_SECONDS = 6000;

    /**
     * Represents 7 seconds in milliseconds.
     */
    private static final long SEVEN_SECONDS = 7000;

    /**
     * Represents 10 seconds in milliseconds.
     */
    private static final long TEN_SECONDS   = 10000;

    /**
     * Instance.
     */
    private static Tester instance = null;

    /**
     * Minimum time to count time in periods.
     */
    private static long minimumTime;

    /**
     * Refresh time for clock.
     */
    private static long refreshTime;

    /**
     * Default constructor.
     *
     * @param newMinimumTime Minimum time to count time in periods
     * @param newRefreshTime Refresh time for clock
     */
    private Tester(final long newMinimumTime, final long newRefreshTime) {
        minimumTime = newMinimumTime;
        refreshTime = newRefreshTime;
    }

    /**
     * GetInstance of Singleton.
     *
     * @param newMinimumTime Minimum time to count time in periods
     * @param newRefreshTime Refresh time for clock
     *
     * @return instance of the class
     */
    public static Tester getInstance(
            final long newMinimumTime,
            final long newRefreshTime) {

        if (instance == null) {
            instance = new Tester(newMinimumTime, newRefreshTime);
        }
        return instance;
    }

    /**
     * Executes the test A1 from timeTracker.pdf .
     *
     * @throws InterruptedException throws only if necessary
     *
     * @throws WrongParamsException precondition of params
     */
    public static void testA1()
            throws InterruptedException, WrongParamsException {

        MyLogger.info("START TEST A1");

        MyLogger.info("Creating tree structure");
        Project projectRoot = new Project("ROOT", "ROOTDESC", null);
        Project projectOne  = new Project("P1", "1", projectRoot);
        Project projectTwo  = new Project("P2", "3", projectOne);
        Task taskOne        = new Task("T1", "4", minimumTime, projectTwo);
        Task taskTwo        = new Task("T2", "5", minimumTime, projectTwo);
        Task taskThree      = new Task("T3", "2", minimumTime, projectOne);

        MyLogger.info("Creating TimeVisitor for testA1");
        TimeVisitor timeVisitor = new TimeVisitor(projectRoot);
        Clock.getInstance().addObserver(timeVisitor);

        // start task3 here
        taskThree.startPeriod();

        // wait 3 seconds
        Thread.sleep(THREE_SECONDS);

        // stop task 3 here
        taskThree.stopPeriod();

        // wait 7 seconds
        Thread.sleep(SEVEN_SECONDS);

        //start task 2 here
        taskTwo.startPeriod();

        // wait 10 seconds
        Thread.sleep(TEN_SECONDS);

        // stop task 2 here
        taskTwo.stopPeriod();

        // start again task 3 here
        taskThree.startPeriod();

        // wait 2 seconds
        Thread.sleep(TWO_SECONDS);

        // taskThree second period stop
        taskThree.stopPeriod();

        MyLogger.info("Creating Saver");
        Saver saver = new serSaver();

        // save the current state
        saver.save(projectRoot);

        // load the previous state
        Project newProject = (Project) saver.load();
        MyLogger.info(newProject.getName() + " has been loaded");

        MyLogger.warning("END TEST A1");
    }

    /**
     * Executes the test A2 from timeTracker.pdf .
     *
     * @throws InterruptedException throws only if necessary
     *
     * @throws WrongParamsException precondition of params
     */
    public static void testA2()
            throws InterruptedException, WrongParamsException {

        MyLogger.info("START TEST A2");

        MyLogger.info("Creating tree structure");
        Project projectRoot = new Project("ROOT", "ROOTDESC", null);
        Project projectOne  = new Project("P1", "1", projectRoot);
        Project projectTwo  = new Project("P2", "3", projectOne);
        Task taskOne        = new Task("T1", "4", minimumTime, projectTwo);
        Task taskTwo        = new Task("T2", "5", minimumTime, projectTwo);
        Task taskThree      = new Task("T3", "2", minimumTime, projectOne);

        MyLogger.info("Creating TimeVisitor for testA2");
        TimeVisitor timeVisitor = new TimeVisitor(projectRoot);
        Clock.getInstance().addObserver(timeVisitor);

        // task 3 starts here
        taskThree.startPeriod();

        // wait 4 seconds
        Thread.sleep(FOUR_SECONDS);

        // task 2 starts here
        taskTwo.startPeriod();

        // wait 2 seconds
        Thread.sleep(TWO_SECONDS);

        // task 3 stops here
        taskThree.stopPeriod();

        // wait 2 seconds
        Thread.sleep(TWO_SECONDS);

        // task one starts here
        taskOne.startPeriod();

        // wait 4 seconds
        Thread.sleep(FOUR_SECONDS);

        // task one stops here
        taskOne.stopPeriod();

        // wait 2 seconds
        Thread.sleep(FOUR_SECONDS);

        // task 2 stops here
        taskTwo.stopPeriod();

        // wait 4 seconds
        Thread.sleep(FOUR_SECONDS);

        // task 3 starts here
        taskThree.startPeriod();

        // wait 2 seconds
        Thread.sleep(FOUR_SECONDS);

        // task 3 stops here
        taskThree.stopPeriod();

        MyLogger.info("Creating Saver");
        Saver saver = new serSaver();

        // save current state
        saver.save(projectRoot);

        // load last state
        Project newProject = (Project) saver.load();
        MyLogger.info(newProject.getName() + " has been loaded");

        MyLogger.info("END TEST A2");
    }

    /**
     * Tests creating a short report.
     *
     * @throws InterruptedException throws only if necessary
     *
     * @throws WrongParamsException precondition of params
     */
    public static void testShortReport()
            throws InterruptedException, WrongParamsException {

        MyLogger.info("START TEST SHORT REPORT");

        MyLogger.info("Creating tree structure");
        Project projectRoot = new Project("ROOT", "", null);
        Project projectOne  = new Project("P1", "I am proj 1", projectRoot);
        Project projectTwo  = new Project("P2", "I am proj 2", projectRoot);
        Project projectOneDotTwo = new Project("P1.2", "I am proj 1.2", projectOne);
        Task taskOne = new Task("T1", "I am task 1", minimumTime, projectOne);
        Task taskTwo = new Task("T2", "I am task 2", minimumTime, projectOne);
        Task taskThree = new Task("T3", "I am task 3", minimumTime, projectTwo);
        Task taskFour  = new Task("T4", "I am task 4", minimumTime, projectOneDotTwo);

        MyLogger.info("Creating TimeVisitor for test short report");
        TimeVisitor timeVisitor = new TimeVisitor(projectRoot);
        Clock.getInstance().addObserver(timeVisitor);

        MyLogger.info("Starting test");
        taskOne.startPeriod();
        taskFour.startPeriod();

        Thread.sleep(FOUR_SECONDS);

        Date initialReportDate = new Date();

        taskOne.stopPeriod();
        taskTwo.startPeriod();

        Thread.sleep(SIX_SECONDS);

        taskTwo.stopPeriod();
        taskFour.stopPeriod();
        taskThree.startPeriod();

        Thread.sleep(FOUR_SECONDS);

        Date finalReportDate = new Date();

        taskThree.stopPeriod();
        taskTwo.startPeriod();

        Thread.sleep(TWO_SECONDS);

        taskThree.startPeriod();

        Thread.sleep(FOUR_SECONDS);

        taskTwo.stopPeriod();
        taskThree.stopPeriod();

        MyLogger.info("Creating short report");
        Report report = new ShortReport(
                initialReportDate,
                finalReportDate,
                projectRoot);

        MyLogger.info("Printing report to HTML and plain text");
        report.printHTML();
        report.printPlainText();

        MyLogger.warning("END TEST SHORT REPORT");
    }

    /**
     * Tests creating a short report.
     *
     * @throws InterruptedException throws only if necessary
     *
     * @throws WrongParamsException precondition of params
     */
    public static void testDetailedReport()
            throws InterruptedException, WrongParamsException {

        MyLogger.info("START TEST DETAILED REPORT");

        MyLogger.info("Creating tree structure");
        Project projectRoot = new Project("ROOT", "", null);
        Project projectOne  = new Project("P1", "I am proj 1", projectRoot);
        Project projectTwo  = new Project("P2", "I am proj 2", projectRoot);
        Project projectOneDotTwo = new Project("P1.2", "I am proj 1.2", projectOne);
        Task taskOne = new Task("T1", "I am task 1", minimumTime, projectOne);
        Task taskTwo = new Task("T2", "I am task 2", minimumTime, projectOne);
        Task taskThree = new Task("T3", "I am task 3", minimumTime, projectTwo);
        Task taskFour  = new Task("T4", "I am task 4", minimumTime, projectOneDotTwo);

        MyLogger.info("Creating TimeVisitor for test detailed report");
        TimeVisitor timeVisitor = new TimeVisitor(projectRoot);
        Clock.getInstance().addObserver(timeVisitor);

        MyLogger.info("Starting test");
        taskOne.startPeriod();
        taskFour.startPeriod();

        Thread.sleep(FOUR_SECONDS);

        Date initialReportDate = new Date();

        taskOne.stopPeriod();
        taskTwo.startPeriod();

        Thread.sleep(SIX_SECONDS);

        taskTwo.stopPeriod();
        taskFour.stopPeriod();
        taskThree.startPeriod();

        Thread.sleep(FOUR_SECONDS);

        Date finalReportDate = new Date();

        taskThree.stopPeriod();
        taskTwo.startPeriod();

        Thread.sleep(TWO_SECONDS);

        taskThree.startPeriod();

        Thread.sleep(FOUR_SECONDS);

        taskTwo.stopPeriod();
        taskThree.stopPeriod();

        MyLogger.info("Creating detailed report");
        Report report = new DetailedReport(
                initialReportDate,
                finalReportDate,
                projectRoot);

        MyLogger.info("Printing report to HTML and plain text");
        report.printHTML();
        report.printPlainText();

        MyLogger.warning("END TEST DETAILED REPORT");
    }
}
