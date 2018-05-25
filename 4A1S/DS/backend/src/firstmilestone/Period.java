package firstmilestone;

/*
 * Imported necessary libraries
 */
import java.io.Serializable;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import secondmilestone.VisitorOperations;

/**
 * Period class.
 * A period has its total time, which will be used for its task
 * parent in order to count total task time
 *
 * @author Ramon 1400214, David 1391968
 *
 */
@SuppressWarnings("serial")
public class Period implements Observer, Serializable {

    /**
     * This variable stores the Date when the period finishes.
     */
    private Date endDate;

    /**
     * This variable stores the Date when the period is created.
     */
    private Date startDate;

    /**
     * We save the duration of the period in seconds.
     */
    private long periodTotalTime;

    /**
     * This is the minimum duration of a Period to consider it valid.
     */
    private long minimumTime;

    /**
     * This variable saves the parent Task of this period.
     */
    private Task parentTask;

    /**
     * Units to convert from seconds to milliseconds.
     */
    private static final long TIME_CONVERSION = 1000;

    /**
     * Period constructor
     * Has a parent task and a minimum time
     *
     * If the time updated is less than the minimum,
     * period's time will remain the same.
     *
     * @param newMinimumTime minimum time a task can count
     * @param newParentTask parent task of period
     *
     * @throws WrongParamsException precondition of params
     */
    public Period(final long newMinimumTime, final Task newParentTask)
            throws WrongParamsException {
        if (newMinimumTime < 0
                || newParentTask == null) {
            throw new WrongParamsException("wrong params period construct");
        }
        this.minimumTime     = newMinimumTime * TIME_CONVERSION;
        this.periodTotalTime = 0;
        this.parentTask      = newParentTask;
        assert this.invariant() : "Incorrect period main attributes";
        //MyLogger.info("New period created");
    }

    /**
     * Getter of the property endDate.
     *
     * @return this.endDate
     */
    public final Date getEndDate() {
        //MyLogger.info("Period getEndDate() called");
        return this.endDate;
    }

    /**
     * Setter of the property endDate.
     *
     * @param newEndDate newEndDate
     */
    public final void setEndDate(final Date newEndDate) {
        this.endDate = newEndDate;
       //MyLogger.info("Period new end date set");
    }

    /**
     * Getter of the property startDate.
     *
     * @return this.startDate
     */
    public final Date getStartDate() {
        //MyLogger.info("Period getStartDate() called");
        return this.startDate;
    }

    /**
     * Setter of the property startDate.
     *
     * @param newStartDate startDate
     */
    public final void setStartDate(final Date newStartDate) {
        this.startDate = newStartDate;
        //MyLogger.info("Period new start date set");
    }

    /**
     * Getter of the property periodTotalTime.
     *
     * @return this.periodTotalTime
     */
    public final long getPeriodTotalTime() {
        //MyLogger.info("Period getPeriodTotalTime() called");
        return this.periodTotalTime;
    }

    /**
     * Getter of the property minimumTime.
     *
     * @return this.minimumTime
     */
    public final long getMinimumTime() {
        //MyLogger.info("Period getMinimumTime() called");
        return this.minimumTime;
    }

    /**
     * Getter of the property parentTask.
     *
     * @return  Returns the task.
     */
    public final Task getParentTask() {
        //MyLogger.info("Period getParentTask() called");
        return this.parentTask;
    }

    /**
     * Starts a period setting the start date as a new date.
     */
    public final void startPeriod() {
        setStartDate(new Date());
        assert this.startDate != null;
        assert this.invariant() : "Incorrect period main attributes";
        //MyLogger.info("Period started");
    }

    /**
     * Stops a period setting its end date.
     */
    public final void stopPeriod() {
        setEndDate(new Date());
        assert this.startDate != null;
        assert this.endDate != null;
        assert (this.startDate.getTime() - this.endDate.getTime() <= 0);
        assert this.invariant() : "Incorrect period main attributes";
        //MyLogger.info("Period stopped");
    }

    /**
     * Updates the period, setting its period total time and end date.
     * Also, updates its parent.
     *
     * @param clock clock from where the period is notified
     * @param time new time
     */
    @Override
    public final void update(final Observable clock, final Object time) {

        if (clock == null
                || time == null
                || (long) time < 0) {
            try {
                throw new WrongParamsException("Wrong params in update period");
            } catch (WrongParamsException e) {
                MyLogger.severe(e.getMessage());
            }
        }

        try {
            //MyLogger.info("Period update() called");
            setEndDate(new Date());
            setPeriodTotalTime((long) time);
            this.parentTask.updateParent(
                    this.startDate,
                    this.endDate, (long) time);
            assert this.endDate != null;
            assert this.periodTotalTime >= 0;
        } catch (WrongParamsException e) {
            MyLogger.severe(e.getMessage());
        } finally {
            assert this.invariant() : "Incorrect period main attributes";
        }
    }

    /**
     * Setter of the property periodTotalTime.
     *
     * If the time received is less than the minimumTime,
     * period totalTime remains.
     * the same, if not, period's total Time gets updated
     *
     * @param time  The periodTotalTime to set.
     */
    private void setPeriodTotalTime(final Object time) {
        if (time == null
                || (long) time < 0) {
            try {
                throw new WrongParamsException("Wrong params in update period");
            } catch (WrongParamsException e) {
                MyLogger.severe(e.getMessage());
            }
        }

        if (Math.abs(this.startDate.getTime() - this.endDate.getTime())
                >= Math.abs(this.minimumTime)) {
            long preTotalTime = this.periodTotalTime;
            //MyLogger.info("Period new total time set");
            this.periodTotalTime += (long) time;
            assert this.periodTotalTime >= 0;
            assert this.periodTotalTime >= preTotalTime;
        } else {
            MyLogger.warning("Period count inferior than minimum time");
        }

        assert this.invariant() : "Incorrect period main attributes";
    }

    /**
     * Accepts visit from a visitor.
     *
     * @param visitor visitor to accept
     *
     * @throws WrongParamsException precondition of params
     */
    public void accept(final Visitor visitor) throws WrongParamsException {
        if (visitor == null) {
            throw new WrongParamsException("Wrong params in accept");
        }
        //MyLogger.info("Period accepted visitor");
        visitor.visit(this);
    }

    /**
     * Accepts a visitor operations.
     *
     * @param visitor visitor to accept
     * @param parent parent task of this period
     * @param periodStartingDate starting date of this period
     * @param periodEndDate end date of this period
     *
     * @throws WrongParamsException precondition of params
     */
    public void accept(
            final VisitorOperations visitor,
            final Task parent,
            final Date periodStartingDate,
            final Date periodEndDate) throws WrongParamsException {
        if (visitor == null
                || parent == null
                || periodStartingDate == null
                || periodEndDate == null) {
            throw new WrongParamsException("Wrong params in accept");
        }
        //MyLogger.info("Period accepted visitor operations");
        visitor.visit(this, this.parentTask, periodStartingDate, periodEndDate);
    }

    /**
     * Period invariant.
     *
     * @return true if main attributes are correct, false otherwise
     */
    protected boolean invariant() {
        /* Start date can be null, because Task creates a period
         * and after creating it, task starts the period created.
         */
        if (this.parentTask != null
                && this.minimumTime > 0
                && this.periodTotalTime >= 0) {
            return true;
        }
        return false;
    }
}
