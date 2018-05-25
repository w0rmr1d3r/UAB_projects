package firstmilestone;

/*
 * Imported necessary libraries
 */
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import secondmilestone.VisitorOperations;

/**
 * Task class, each class has periods which form the task total time.
 * for its project
 *
 * @author Ramon 1400214, David 1391968
 *
 */
@SuppressWarnings("serial")
public class Task extends Work {

    /**
     * List of the Periods of a Task.
     */
    private List<Period> periodList;

    /**
     * Minimum duration of a period to consider it valid.
     */
    private long minimumPeriodTime;

    /**
     * Number four, 4.
     */
    private static final int FOUR = 4;

    /**
     * Task, see work constructor for more info.
     * Needs minimumProjectTime because each project.
     * of this task will have a minimum time too.
     * See setPeriodTotalTime(...) for more info.
     *
     * @param name task's name
     * @param description task's description
     * @param newMinimumPeriodTime task's period's minimum time
     * @param parentProject task's parent
     *
     * @throws WrongParamsException precondition of params
     */
    public Task(
            final String name,
            final String description,
            final long newMinimumPeriodTime,
            final Project parentProject) throws WrongParamsException {

        super(name, description, parentProject);

        if (newMinimumPeriodTime < 0) {
            throw new WrongParamsException("Wrong params in task constructor");
        }

        this.minimumPeriodTime = newMinimumPeriodTime;
        this.periodList        = new ArrayList<Period>();
        assert this.invariant() : "Incorrect task main attributes";
        //MyLogger.info("New task created");
    }

    /**
     * Getter of the property minimumPeriodTime.
     *
     * @return this.minimumPeriodTime
     */
    public final long getMinimumPeriodTime() {
        //MyLogger.info("Task getMinimumPeriodTime called");
        return this.minimumPeriodTime;
    }

    /**
     * Gets list of periods.
     *
     * @return perdioList
     */
    public final List<Period> getPeriodList() {
        //MyLogger.info("Task getPeriodList() called");
        return this.periodList;
    }

    /**
     * Creates a new period and adds it to task's period list.
     * Also, the period is add to the clock as an observer.
     *
     * @throws WrongParamsException precondition of params
     */
    private void addPeriod() throws WrongParamsException {
        if (this.periodList == null) {
            throw new NullPointerException("Periodlist not initialized");
        }
        int preSize = this.periodList.size();
        Period newPeriod = new Period(this.minimumPeriodTime, this);
        this.periodList.add(newPeriod);
        Clock.getInstance().addObserver(newPeriod);

        assert preSize == this.periodList.size() - 1;
        assert newPeriod.getParentTask() == this;
        assert this.invariant() : "Incorrect task main attributes";
        assert newPeriod.invariant() : "Period invariant in addPeriod";
        //MyLogger.info("Task added new period");
    }

    /**
     * Starts a period for this task.
     *
     * @throws WrongParamsException precondition of params
     */
    public void startPeriod() throws WrongParamsException {
        addPeriod();
        periodList.get(periodList.size() - 1).startPeriod();
        assert this.invariant() : "Incorrect task main attributes";
        //MyLogger.info("Task started period");
    }

    /**
     * Stops the current and last period.
     * Removes the period from clock's observer list.
     *
     * @throws WrongParamsException precondition of params
     */
    public void stopPeriod() throws WrongParamsException {
        if (this.periodList == null || this.periodList.size() <= 0) {
            throw new WrongParamsException("Cannot stop a void list");
        }
        this.periodList.get(periodList.size() - 1).stopPeriod();
        Clock.getInstance()
            .deleteObserver(periodList.get(periodList.size() - 1));
        assert periodList.get(periodList.size() - 1).getPeriodTotalTime() >= 0;
        assert this.invariant() : "Incorrect task main attributes";
        //MyLogger.info("Task stopped period");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(final Visitor visitor) throws WrongParamsException {
        if (visitor == null) {
            throw new WrongParamsException("Wrong params in accept");
        }
        //MyLogger.info("Task accepted visitor");
        visitor.visit(this);
        assert this.invariant() : "Incorrect task main attributes";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(
            final VisitorOperations visitor,
            final Date workStartingDate,
            final Date workEndDate) throws WrongParamsException {
        if (visitor == null
                || workStartingDate == null
                || workEndDate == null) {
            throw new WrongParamsException("Wrong params in accept");
        }
        if (this.getStartingDate() != null) {
            visitor.visit(this, workStartingDate, workEndDate);
            visitor.setValueCurrentRow(FOUR, this.getParentProject().getName());
        }
        //MyLogger.info("Task accepted visitor operations");
        assert this.invariant() : "Incorrect task main attributes";
    }

    /**
     * Invariant of task.
     *
     * @return true if params are correct, false otherwise
     */
    @Override
    protected boolean invariant() {
        if (this.periodList != null
                && this.periodList.size() >= 0
                && this.minimumPeriodTime > 0
                && super.invariant()) {
            return true;
        }
        return false;
    }
}
