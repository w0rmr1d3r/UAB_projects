package firstmilestone;

/*
 * Imported necessary libraries
 */
import java.util.Observable;

/**
 * Clock class - Singleton
 * We need a clock in order to count periods time.
 * Clock uses the observer/observable pattern
 * Clock updates observers each refreshTime
 *
 * @author Ramon 1400214, David 1391968
 *
 */
public final class Clock extends Observable implements Runnable {

    /**
     * Instance of class.
     */
    private static Clock instance = null;

    /**
     * This variable indicates when the clock refreshes.
     */
    private long refreshTime;

    /**
     * With running parameter, we are able to stop the clock.
     */
    private boolean running;

    /**
     * Convert from seconds to milliseconds with an error check.
     * we multiply by 1010 instead of 1000
     * in order to avoid clock mistakes
     * In some computers, multiplying by 1000 generates
     * a good output and in others it does not.
     */
    private static final long TIME_CONVERSION = 1010;

    /**
     * Clock constructor.
     * Creates the clock, sets the refreshTime and sets running to True.
     *
     * @param newRefreshTime time for every tick to update periods
     *
     * @throws WrongParamsException precondition of params
     */
    private Clock(final long newRefreshTime) throws WrongParamsException {
        if (newRefreshTime <= 0) {
            throw new WrongParamsException(
                    "Clock refresh time must be greater than 0"
                    );
        }
        this.refreshTime = newRefreshTime * TIME_CONVERSION;
        this.running     = true;
        //MyLogger.info("Clock created with refresh time: " + this.refreshTime);
    }

    /**
     * Gets instance of singleton Clock.
     *
     * @param newRefreshTime refresh time in case the instance is null
     *
     * @return Clock instance of singleton
     *
     * @throws WrongParamsException precondition of params
     */
    public static Clock getInstance(final long newRefreshTime)
            throws WrongParamsException {
        if (newRefreshTime <= 0) {
            throw new WrongParamsException(
                    "Clock refresh time must be greater than 0"
                    );
        }
        //MyLogger.info("Getting instance of clock");
        if (instance == null) {
            instance = new Clock(newRefreshTime);
        }
        return instance;
    }

    /**
     * Gets default instance of singleton Clock.
     *
     * @return Clock instance of singleton
     *
     * @throws WrongParamsException precondition of params
     */
    public static Clock getInstance() throws WrongParamsException {
        //MyLogger.info("Getting default instance of clock");
        if (instance == null) {
            instance = new Clock(-1);
        }
        return instance;
    }

    /**
     * Runs the clock.
     */
    @Override
    public void run() {
        //MyLogger.info("Clock run function called");
        while (this.running) {
            try {
                assert this.invariant() : "Wrong attributes of clock";
                this.tick();
                //MyLogger.info("Clock ticked");
                Thread.sleep(this.refreshTime);
            } catch (InterruptedException e) {
                MyLogger.severe(
                        "InterrumptedException in clock: " + e.getMessage()
                        );
            }
        }
        assert this.invariant() : "Wrong attributes of clock";
    }

    /**
     * Returns refresh time.
     *
     * @return refreshTime (long)
     */
    public long getRefreshTime() {
        //MyLogger.info("Get refresh time from clock called");
        return this.refreshTime;
    }

    /**
     * Clock's tick.
     * The clock notifies the time to the  Observers.
     */
    public void tick() {
        //MyLogger.info("Clock tick called");
        setChanged();
        notifyObservers(this.getRefreshTime());
        assert this.invariant() : "Wrong attributes of clock";
    }

    /**
     * Stops the clock by setting running to false.
     */
    public void stop() {
        MyLogger.info("Clock has been stopped");
        this.running = false;
        assert this.invariant() : "Wrong attributes of clock";
    }

    /**
     * Invariant of clock.
     *
     * @return true if conditions are correct, false otherwise
     */
    protected boolean invariant() {
        if (instance != null
                && this.refreshTime > 0
                && (this.running || !this.running)) {
            return true;
        }
        return false;
    }
}
