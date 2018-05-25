package secondmilestone;

/*
 * Imported necessary libraries.
 */
import java.util.Date;
import firstmilestone.Period;
import firstmilestone.Project;
import firstmilestone.Task;
import firstmilestone.WrongParamsException;

/**
 * Visits works and periods.
 * With extra params than the firstmilestone.Visitor
 *
 * @author Ramon 1400214, David 1391968
 *
 */
public interface VisitorOperations {

    /**
     * Visits a task.
     *
     * @param taskVisited task to visit
     * @param startingDate start date of the report
     * @param endDate end date of the report
     *
     * @throws WrongParamsException if params are not correct
     */
    void visit(Task taskVisited, Date startingDate, Date endDate)
            throws WrongParamsException;

    /**
     * Visits a project.
     *
     * @param projectVisited project to visit
     * @param startingDate start date of the report
     * @param endDate end date of the report
     *
     * @throws WrongParamsException if params are not correct
     */
    void visit(Project projectVisited, Date startingDate, Date endDate)
            throws WrongParamsException;

    /**
     * Visits a period/interval.
     *
     * @param periodVisited period to visit.
     * @param parent parent task of period
     * @param startingDate start date of the report
     * @param endDate end date of the report
     *
     * @throws WrongParamsException if params are not correct
     */
    void visit(
            Period periodVisited,
            Task parent,
            Date startingDate,
            Date endDate) throws WrongParamsException;

    /**
     * Sets value to a row.
     *
     * @param column column to set value
     * @param value value to set
     *
     * @throws WrongParamsException if params are not correct
     */
    void setValueCurrentRow(
            int column,
            String value) throws WrongParamsException;
}

