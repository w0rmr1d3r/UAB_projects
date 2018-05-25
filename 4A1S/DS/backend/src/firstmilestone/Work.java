package firstmilestone;

/*
 * Imported necessary libraries
 */
import java.io.Serializable;
import java.util.Date;
import secondmilestone.VisitorOperations;

/**
 * Work, class where task and project heritages from.
 * Composite pattern in order to reduce code.
 *
 * @author Ramon 1400214, David 1391968
 *
 */
@SuppressWarnings("serial")
public abstract class Work implements Serializable {

    /**
     * Work finalization date.
     */
    private Date endDate;

    /**
     * Work starting date.
     */
    private Date startingDate;

    /**
     * Work creation date.
     */
    private Date creationDate;

    /**
     * Work total time.
     */
    private long totalTime;

    /**
     * Work description.
     */
    private String description;

    /**
     * Work name.
     */
    private String name;

    /**
     * Project parent.
     */
    private Project parentProject;

    /**
     * Work constructor.
     * Parent parameter is given in order to visit the works tree
     *
     * @param newName work's name
     * @param newDescription work's description
     * @param newParent work's parent, null if newParent is root
     *
     * @throws WrongParamsException precondition of params
     */
    public Work(
            final String newName,
            final String newDescription,
            final Project newParent) throws WrongParamsException {

        if (newName == null || newDescription == null) {
            throw new WrongParamsException("Some params were incorrect");
        }

        this.name          = newName;
        this.description   = newDescription;
        this.totalTime     = 0;
        this.parentProject = newParent;
        this.creationDate  = new Date();
        this.startingDate  = null;
        this.endDate       = null;

        if (this.parentProject != null) {
            this.parentProject.addSubWork(this);
        }

        //assert this.invariant() : "Work invariant incorrect";
        //MyLogger.info("New work created");
    }

    /**
     * Getter of the property endDate.
     *
     * @return Returns the endDate.
     */
    public Date getEndDate() {
        //MyLogger.info("Work getEndDate() called");
        return this.endDate;
    }

    /**
     * Getter of the property startingDate.
     *
     * @return Returns the startingDate.
     */
    public Date getStartingDate() {
        //MyLogger.info("Work getStartingDate() called");
        return this.startingDate;
    }

    /**
     * Setter of the property startingDate.
     *
     * @param newStartingDate The new starting date to set.
     */
    public void setStartingDate(final Date newStartingDate) {
        this.startingDate = newStartingDate;
        //MyLogger.info("Work new starting date set");
    }

    /**
     * Getter of property creationDate.
     *
     * @return Returns work creation date
     */
    public Date getCreationDate() {
        //MyLogger.info("Work getCreationDate called");
        return this.creationDate;
    }

    /**
     * Getter of the property totalTime.
     *
     * @return Returns the totalTime.
     */
    public long getTotalTime() {
        //MyLogger.info("Work getTotalTime() called");
        return this.totalTime;
    }

    /**
     * Setter of the property totalTime.
     *
     * @param newTotalTime  The totalTime to set.
     */
    public void setTotalTime(final long newTotalTime) {
        this.totalTime = newTotalTime;
        //MyLogger.info("Work new total time set");
    }

    /**
     * Getter of the property description.
     *
     * @return Returns the description.
     */
    public String getDescription() {
        //MyLogger.info("Work getDescription() called");
        return this.description;
    }

    /**
     * Getter of the property name.
     *
     * @return Returns the name.
     */
    public String getName() {
        //MyLogger.info("Work getName() called");
        return this.name;
    }

    /**
     * Getter of the property parent project.
     *
     * @return Returns the parent project.
     */
    public Project getParentProject() {
        //MyLogger.info("Work getParentProject() called");
        return this.parentProject;
    }

    /**
     * Setter of the property parent.
     *
     * @param newParent The parent to set.
     */
    public void setParentProject(final Project newParent) {
        this.parentProject = newParent;
        //MyLogger.info("Work new project parent set");
    }

    /**
     * Updates work's parent with a new time, new end date and if necessary,
     * a new starting date.
     *
     * @param sonStartDate start date of son
     * @param sonEndDate last end date for current work
     * @param periodTotalTime time to add to current work
     *
     * @throws WrongParamsException precondition of params
     */
    public void updateParent(
            final Date sonStartDate,
            final Date sonEndDate,
            final long periodTotalTime) throws WrongParamsException {

        if (sonStartDate == null || sonEndDate == null || periodTotalTime < 0) {
            throw new WrongParamsException("Wrong params in updating parent");
        }

        //MyLogger.info("Work updating parent");

        if (this.startingDate == null) {
            this.startingDate = sonStartDate;
        }

        this.endDate    = sonEndDate;
        this.totalTime += periodTotalTime;

        if (this.parentProject != null) {
            this.parentProject
            .updateParent(sonStartDate, sonEndDate, periodTotalTime);
        }

        assert this.startingDate != null;
        assert this.endDate != null;
        assert this.totalTime > 0;

        assert this.invariant() : "Work invariant incorrect";
    }

    /**
     * Accepts a visitor.
     *
     * @param visitor visitor to accept
     *
     * @throws WrongParamsException precondition of params
     */
    public abstract void accept(Visitor visitor) throws WrongParamsException;

    /**
     * Accepts a visitor operations.
     *
     * @param visitor visitor to accept
     * @param workStartingDate starting date of the work
     * @param workEndDate end date of the work
     *
     * @throws WrongParamsException precondition of params
     */
    public abstract void accept(
            VisitorOperations visitor,
            Date workStartingDate,
            Date workEndDate) throws WrongParamsException;

    /**
     * Invariant of work.
     * Classes extending work must override this method while
     * checking its super.invariant and their invariants.
     *
     * @return true if each param is correct, false otherwise
     */
    protected boolean invariant() {
       if (this.name != null
               && this.description != null
               && this.totalTime >= 0
               && this.creationDate != null) {
           return true;
       }
       return false;
    }
}
