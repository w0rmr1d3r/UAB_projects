package secondmilestone;

/*
 * Imported necessary libraries.
 */
import java.util.Date;
import firstmilestone.Period;
import firstmilestone.Project;
import firstmilestone.Task;
import firstmilestone.TimePrinter;
import firstmilestone.WrongParamsException;

/**
 * Visitor implemented to generate tables.
 *
 * @author Ramon 1400214, David 1391968
 *
 */
public class ReportTableOperation implements VisitorOperations {

    /**
     * Table to create.
     */
    private Table table;

    /**
     * Number of iteration, used to see the table row.
     */
    private int iteration;

    /**
     * Time printer to use when printing dates.
     */
    private static TimePrinter timePrinter;

    /**
     * Number zero, 0.
     */
    private static final int ZERO = 0;

    /**
     * Number one, 1.
     */
    private static final int ONE = 1;

    /**
     * Number two, 2.
     */
    private static final int TWO = 2;

    /**
     * Number three, 3.
     */
    private static final int THREE = 3;

    /**
     * Number four, 4.
     */
    private static final int FOUR = 4;

    /**
     * Number five, 5.
     */
    private static final int FIVE = 5;

    /**
     * Constructor.
     *
     * @param newTable table to generate
     *
     * @throws WrongParamsException if params are not correct
     */
    public ReportTableOperation(final Table newTable)
            throws WrongParamsException {
        if (newTable == null) {
            throw new WrongParamsException();
        }
        this.iteration = ONE;
        this.table     = newTable;
        timePrinter    = TimePrinter.getInstance();
        assert this.iteration == ONE;
        assert this.table != null;
        assert timePrinter != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(
            final Period period,
            final Task parent,
            final Date startDate,
            final Date endDate) throws WrongParamsException {
        if (period == null
                || parent == null
                || startDate == null
                || endDate == null
                || (startDate.getTime() - endDate.getTime() > 0)) {
            throw new WrongParamsException();
        }
        int preIteration = this.iteration;
        if (period.getStartDate().compareTo(startDate) >= ZERO) {
            if (period.getEndDate().compareTo(endDate) < ZERO) {
                table.setPosition(
                        iteration,
                        ZERO,
                        period
                        .getParentTask()
                        .getParentProject()
                        .getName() + "  ");
                table.setPosition(
                        iteration,
                        ONE,
                        period.getParentTask().getName() + "  ");
                table.setPosition(
                        iteration,
                        THREE,
                        timePrinter.printDate(period.getStartDate()) + "  ");
                table.setPosition(
                        iteration,
                        FOUR,
                        timePrinter.printDate(period.getEndDate()) + "  ");
                table.setPosition(
                        iteration,
                        FIVE,
                        timePrinter.printTotalTime(period.getPeriodTotalTime())
                        + "  ");
            } else if (period.getEndDate().compareTo(endDate) >= ZERO) {
                table.setPosition(
                        iteration,
                        ZERO,
                        period.getParentTask().getParentProject().getName()
                        + "  ");
                table.setPosition(
                        iteration,
                        ONE,
                        period.getParentTask().getName() + "  ");
                table.setPosition(
                        iteration,
                        THREE,
                        timePrinter.printDate(period.getStartDate()) + "  ");
                table.setPosition(
                        iteration,
                        FOUR,
                        timePrinter.printDate(period.getEndDate()) + "  ");
                table.setPosition(
                        iteration,
                        FIVE,
                        timePrinter.printTimeBetweenTwoDates(
                                period.getStartDate(),
                                endDate));
            }
        } else if (period.getStartDate().compareTo(startDate) <= ZERO) {
            if (period.getEndDate().compareTo(endDate) >= ZERO) {
                table.setPosition(
                        iteration,
                        ZERO,
                        period.getParentTask().getParentProject().getName()
                        + "  ");
                table.setPosition(
                        iteration,
                        ONE,
                        period.getParentTask().getName() + "  ");
                table.setPosition(
                        iteration,
                        THREE,
                        timePrinter.printDate(period.getStartDate()) + "  ");
                table.setPosition(
                        iteration,
                        FOUR,
                        timePrinter.printDate(period.getEndDate()) + "  ");
                table.setPosition(
                        iteration,
                        FIVE,
                        timePrinter
                        .printTimeBetweenTwoDates(startDate, endDate));
            }
            if (period.getEndDate().compareTo(endDate) < ZERO
                    && period.getStartDate()
                    .compareTo(
                            new Date(period.getStartDate().getTime()
                                    + period.getPeriodTotalTime())) > ZERO) {
                table.setPosition(
                        iteration,
                        ZERO,
                        period.getParentTask().getParentProject().getName()
                        + "  ");
                table.setPosition(
                        iteration,
                        ONE,
                        period.getParentTask().getName() + "  ");
                table.setPosition(
                        iteration,
                        THREE,
                        timePrinter.printDate(period.getStartDate()) + "  ");
                table.setPosition(
                        iteration,
                        FOUR,
                        timePrinter.printDate(period.getEndDate()) + "  ");
                table.setPosition(
                        iteration,
                        FIVE,
                        timePrinter
                        .printTimeBetweenTwoDates(
                                startDate,
                                period.getEndDate()
                                )
                        );
            }
        }
        iteration++;
        assert iteration >= 1;
        assert preIteration < iteration;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(
            final Task task,
            final Date startDate,
            final Date endDate) throws WrongParamsException {
        if (task == null || (startDate.getTime() - endDate.getTime() > 0)) {
            throw new WrongParamsException();
        }
        int preIteration = this.iteration;
        if (task.getStartingDate().compareTo(startDate) >= ZERO) {
            if (task.getEndDate().compareTo(endDate) < ZERO) {
                table.setPosition(iteration, ZERO, task.getName() + "  ");
                table.setPosition(
                        iteration,
                        ONE,
                        timePrinter.printDate(task.getStartingDate()) + "  ");
                table.setPosition(
                        iteration,
                        TWO,
                        timePrinter.printDate(task.getEndDate()) + "  ");
                table.setPosition(
                        iteration,
                        THREE,
                        timePrinter.printTotalTime(task.getTotalTime()) + "  ");
            } else if (task.getEndDate().compareTo(endDate) >= ZERO) {
                table.setPosition(iteration, ZERO, task.getName() + "  ");
                table.setPosition(
                        iteration,
                        ONE,
                        timePrinter.printDate(task.getStartingDate()) + "  ");
                table.setPosition(
                        iteration,
                        TWO,
                        timePrinter.printDate(task.getEndDate()) + "  ");
                table.setPosition(
                        iteration,
                        THREE,
                        timePrinter.printTimeBetweenTwoDates(
                                task.getStartingDate(),
                                endDate));
            }
        } else if (task.getStartingDate().compareTo(startDate) <= ZERO) {
            if (task.getEndDate().compareTo(endDate) >= ZERO) {
                table.setPosition(iteration, ZERO, task.getName() + "  ");
                table.setPosition(
                        iteration,
                        ONE,
                        timePrinter.printDate(task.getStartingDate()) + "  ");
                table.setPosition(
                        iteration,
                        TWO,
                        timePrinter.printDate(task.getEndDate()) + "  ");
                table.setPosition(
                        iteration,
                        THREE,
                        timePrinter
                        .printTimeBetweenTwoDates(startDate, endDate));
            }
            if (task.getEndDate().compareTo(endDate) < ZERO
                    && task.getStartingDate()
                    .compareTo(new Date(task.getStartingDate().getTime()
                            + task.getTotalTime())) > ZERO) {
                table.setPosition(iteration, ZERO, task.getName() + "  ");
                table.setPosition(
                        iteration,
                        ONE,
                        timePrinter.printDate(task.getStartingDate()) + "  ");
                table.setPosition(
                        iteration,
                        TWO,
                        timePrinter.printDate(task.getEndDate()) + "  ");
                table.setPosition(
                        iteration,
                        THREE,
                        timePrinter
                        .printTimeBetweenTwoDates(
                                startDate,
                                task.getEndDate()));
            }
        }
        iteration++;
        assert iteration >= 1;
        assert preIteration < iteration;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(
            final Project project,
            final Date startDate,
            final Date endDate) throws WrongParamsException {
        if (project == null || (startDate.getTime() - endDate.getTime() > 0)) {
            throw new WrongParamsException();
        }
        int preIteration = this.iteration;
        if (project.getStartingDate().compareTo(startDate) >= ZERO) {
            if (project.getEndDate().compareTo(endDate) < ZERO) {
                table.setPosition(iteration, ZERO, project.getName() + "  ");
                table.setPosition(
                        iteration,
                        ONE,
                        timePrinter.printDate(project.getStartingDate())
                        + "  ");
                table.setPosition(
                        iteration,
                        TWO,
                        timePrinter.printDate(project.getEndDate()) + "  ");
                table.setPosition(
                        iteration,
                        THREE,
                        timePrinter.printTotalTime(project.getTotalTime())
                        + "  ");
            } else if (project.getEndDate().compareTo(endDate) >= ZERO) {
                table.setPosition(
                        iteration,
                        ZERO,
                        project.getName() + "  ");
                table.setPosition(
                        iteration,
                        ONE,
                        timePrinter.printDate(project.getStartingDate())
                        + "  ");
                table.setPosition(
                        iteration,
                        TWO,
                        timePrinter.printDate(project.getEndDate())
                        + "  ");
                table.setPosition(
                        iteration,
                        THREE,
                        timePrinter
                        .printTimeBetweenTwoDates(
                                project.getStartingDate(),
                                endDate));
            }
        } else if (project.getStartingDate().compareTo(startDate) <= ZERO) {
            if (project.getEndDate().compareTo(endDate) >= ZERO) {
                table.setPosition(iteration, ZERO, project.getName() + "  ");
                table.setPosition(
                        iteration,
                        ONE,
                        timePrinter.printDate(project.getStartingDate())
                        + "  ");
                table.setPosition(
                        iteration,
                        TWO,
                        timePrinter.printDate(project.getEndDate())
                        + "  ");
                table.setPosition(
                        iteration,
                        THREE,
                        timePrinter.printTimeBetweenTwoDates(
                                startDate,
                                endDate));
            }
            if (project.getEndDate().compareTo(endDate) < ZERO
                    && project.getStartingDate()
                    .compareTo(
                            new Date(project.getStartingDate().getTime()
                                    + project.getTotalTime())) > ZERO) {
                table.setPosition(iteration, ZERO, project.getName() + "  ");
                table.setPosition(
                        iteration,
                        ONE,
                        timePrinter.printDate(project.getStartingDate())
                        + "  ");
                table.setPosition(
                        iteration,
                        TWO,
                        timePrinter.printDate(project.getEndDate())
                        + "  ");
                table.setPosition(
                        iteration,
                        THREE,
                        timePrinter.printTimeBetweenTwoDates(
                                startDate,
                                project.getEndDate()));
            }
        }
        iteration++;
        assert iteration >= 1;
        assert preIteration < iteration;
    }

    /**
     * Gets table.
     *
     * @return table this table
     */
    public Table getTable() {
        return this.table;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setValueCurrentRow(
            final int column,
            final String value) throws WrongParamsException {
        if (column < 0 || value == null) {
            throw new WrongParamsException();
        }
        table.setPosition(iteration - 1, column, "  " + value);
    }
}
