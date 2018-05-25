package firstmilestone;

/*
 * Imported necessary libraries
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import secondmilestone.VisitorOperations;

/**
 * Project is a type of work where it contains more works (tasks or projects).
 * Each project defines a type of work for the user
 *
 * @author Ramon 1400214, David 1391968
 *
 */
@SuppressWarnings("serial")
public class Project extends Work {

    /**
     * List of sub projects and/or tasks of this Project.
     */
    private List<Work> subWorkList;

    /**
     * Project constructor, see work constructor.
     *
     * @param name project's name
     * @param description project's description
     * @param parentProject project's parent, null if project is root
     *
     * @throws WrongParamsException precondition of params
     */
    public Project(
            final String name,
            final String description,
            final Project parentProject) throws WrongParamsException {

        super(name, description, parentProject);
        this.subWorkList = new ArrayList<Work>();
        assert this.invariant() : "Project invariant incorrect";
        //MyLogger.info("New project created");
    }

    /**
     * Gets sub work list.
     *
     * @return this.subWorkList
     */
    public final List<Work> getSubWorkList() {
        //MyLogger.info("Project getSubWorkList called");
        return this.subWorkList;
    }

    /**
     * Adds a work to the end of subWorkList.
     *
     * @param newSubWork work to add
     *
     * @throws WrongParamsException precondition of params
     */
    public final void addSubWork(final Work newSubWork)
            throws WrongParamsException {
        if (newSubWork == null) {
            throw new WrongParamsException("Wrong params in add sub work");
        }
        int preSize = this.subWorkList.size();
        this.subWorkList.add(newSubWork);

        assert this.subWorkList.size() >= 1;
        assert preSize == this.subWorkList.size() - 1;
        assert this.invariant() : "Project invariant incorrect";
        //MyLogger.info("Project new sub work added");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(final Visitor visitor) throws WrongParamsException {
        if (visitor == null) {
            throw new WrongParamsException("Wrong params in accept");
        }
        //MyLogger.info("Project accepted visitor");
        visitor.visit(this);
        assert this.invariant() : "Project invariant incorrect";
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
        //MyLogger.info("Project accepted visitor operations");
        visitor.visit(this, workStartingDate, workEndDate);
        assert this.invariant() : "Project invariant incorrect";
    }

    /**
     * Invariant for project.
     *
     * @return true if params are correct, false otherwise
     */
    @Override
    protected boolean invariant() {
        if (super.invariant()
                && this.subWorkList != null
                && this.subWorkList.size() >= 0) {
            return true;
        }
        return false;
    }
}
