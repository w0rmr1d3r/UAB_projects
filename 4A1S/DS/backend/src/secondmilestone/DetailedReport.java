package secondmilestone;

/*
 * Imported necessary libraries.
 */
import java.util.Date;
import java.util.List;
import firstmilestone.Period;
import firstmilestone.Project;
import firstmilestone.Saver;
import firstmilestone.Task;
import firstmilestone.TimePrinter;
import firstmilestone.Work;
import firstmilestone.WrongParamsException;
import firstmilestone.htmlSaver;
import firstmilestone.txtSaver;

/**
 * Detailed report.
 *
 * @author Ramon 1400214, David 1391968
 *
 */
public class DetailedReport extends Report {

    /**
     * Saver used to save the report.
     */
    private Saver saver;

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
     * @param startDate date where the report starts
     * @param endDate date when the report ends
     * @param rootProject root project of the application
     *
     * @throws WrongParamsException if params are not correct
     */
    public DetailedReport(
            final Date startDate,
            final Date endDate,
            final Project rootProject) throws WrongParamsException {
        super(startDate, endDate);
        if (rootProject == null) {
            throw new WrongParamsException();
        }
        addElement(new Line());
        addElement(new Title("Informe Detallat"));
        addElement(new Line());
        addElement(new SubTitle("Periode"));
        addElement(createHeadTable(rootProject));
        addElement(new Line());
        addElement(new SubTitle("Projectes Arrel"));
        addElement(createRootProjectTable(rootProject));
        addElement(new Line());
        addElement(new SubTitle("SubProjectes"));
        addElement(createSubProjectTable(rootProject));
        addElement(new Line());
        addElement(new SubTitle("Tasques"));
        addElement(createTaskTable(rootProject));
        addElement(new Line());
        addElement(new SubTitle("Intervals"));
        addElement(createPeriodTable(rootProject));
        addElement(new Line());
        addElement(new Paragraph("TimeTracker v2.0"));
        assert this.elements != null;
        assert this.elements.size() == FIVE * THREE + FOUR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void printHTML() throws WrongParamsException {
        this.saver               = new htmlSaver();
        String textInformeHTML   = "";
        HTMLElementToTag visitor = new HTMLElementToTag();

        for (Element element : elements) {
            element.accept(visitor);
        }

        textInformeHTML = visitor.getWebPageText();
        this.saver.save(textInformeHTML);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void printPlainText() throws WrongParamsException {
        this.saver         = new txtSaver();
        String textInforme = "";

        for (Element element : getElements()) {
            textInforme += element.getText() + "\r\n"; // Windows
            //textInforme += element.getText() + "\n"; // Linux
        }

        this.saver.save(textInforme);
    }

    /**
     * Creates header table.
     *
     * @param rootProject root project to get info from
     *
     * @return header table
     *
     * @throws WrongParamsException precondition of params
     */
    private Table createHeadTable(final Project rootProject)
            throws WrongParamsException {
        if (rootProject == null) {
            throw new WrongParamsException();
        }

        Table periodTable = new Table(FOUR, TWO);
        periodTable.setPosition(ZERO, ONE, "Date:  ");
        periodTable.setPosition(ONE, ZERO, "Start Date:  ");
        periodTable.setPosition(ONE, ONE, printStartDate());
        periodTable.setPosition(TWO, ZERO, "End Date:  ");
        periodTable.setPosition(TWO, ONE, printEndDate());
        periodTable.setPosition(THREE, ZERO, "Report Date:  ");
        periodTable.setPosition(THREE, ONE,
                TimePrinter.getInstance().printDate(new Date())
                );

        assert periodTable != null;
        return periodTable;
    }

    /**
     * Creates table of root project.
     *
     * @param rootProject root project
     *
     * @return table of projects
     *
     * @throws WrongParamsException precondition of params
     */
    private Table createRootProjectTable(final Project rootProject)
            throws WrongParamsException {
        if (rootProject == null
                || rootProject.getSubWorkList() == null
                || rootProject.getSubWorkList().size() <= ZERO) {
            throw new WrongParamsException();
        }
        List<Work> subProjectList = rootProject.getSubWorkList();
        int projectsNumber        = rootProject.getSubWorkList().size();
        Table projectTable        = new Table(projectsNumber + ONE, FOUR);

        projectTable.setPosition(ZERO, ZERO, "Project Name:  ");
        projectTable.setPosition(ZERO, ONE, "  Start Date:  ");
        projectTable.setPosition(ZERO, TWO, "  End Date:  ");
        projectTable.setPosition(ZERO, THREE, "  Total Time:");

        ReportTableOperation visitor = new ReportTableOperation(projectTable);

        for (Work subProject : subProjectList) {
            if (subProject instanceof Project) {
                subProject.accept(
                        visitor,
                        getStartDate(),
                        getEndDate());
            }
        }

        assert visitor.getTable() != null;
        return visitor.getTable();
    }

    /**
     * Creates the table with subprojects.
     *
     * @param rootProject root project of the time tracker
     *
     * @return table of sub projects
     *
     * @throws WrongParamsException precondition of params
     */
    private Table createSubProjectTable(final Project rootProject)
            throws WrongParamsException {
        if (rootProject == null) {
            throw new WrongParamsException();
        }
        List<Work> subProjectList = rootProject.getSubWorkList();
        Table subProjectTable     = new Table(
                getTotalProjectsInTree(rootProject),
                FIVE);

        subProjectTable.setPosition(ZERO, ZERO, "Project Name:  ");
        subProjectTable.setPosition(ZERO, ONE, "  Start Date:  ");
        subProjectTable.setPosition(ZERO, TWO, "  End Date:  ");
        subProjectTable.setPosition(ZERO, THREE, "  Total Time:");
        subProjectTable.setPosition(ZERO, FOUR, "  Parent:");

        ReportTableOperation subProjVisitor =
                new ReportTableOperation(subProjectTable);
        for (Work subProject : subProjectList) {
            subProjVisitor = addBranchProjectToTable(
                    subProject,
                    subProjVisitor);
        }

        assert subProjVisitor.getTable() != null;
        return subProjVisitor.getTable();
    }

    /**
     * Adds project row to table.
     *
     * @param parentWork parent work
     * @param visitor visitor to work with
     *
     * @return visitor to get table from
     *
     * @throws WrongParamsException precondition of params
     */
    private ReportTableOperation addBranchProjectToTable(
            final Work parentWork,
            ReportTableOperation visitor)  throws WrongParamsException {

        if (parentWork == null || visitor == null) {
            throw new WrongParamsException();
        }
        List<Work> subProjectList = ((Project) parentWork).getSubWorkList();
        if (subProjectList.size() == ZERO) {
            return visitor;
        }
        for (Work subProject : subProjectList) {
            if (subProject instanceof Project) {
                subProject.accept(
                        visitor,
                        getStartDate(),
                        getEndDate());
                visitor.setValueCurrentRow(FOUR, parentWork.getName());
                visitor = addBranchProjectToTable(subProject, visitor);
            }
        }
        return visitor;
    }

    /**
     * Gets the total number of projects in the tree structure.
     *
     * @param parentWork parent work
     *
     * @return number of tasks from parent
     *
     * @throws WrongParamsException precondition of params
     */
    private int getTotalProjectsInTree(final Work parentWork)
            throws WrongParamsException {
        if (parentWork == null) {
            throw new WrongParamsException();
        }
        if (parentWork instanceof Task) {
            return ZERO;
        }
        List<Work> subProjectList = ((Project) parentWork).getSubWorkList();
        int counter = ONE;
        if (subProjectList.size() == ZERO) {
            return counter;
        }
        for (Work subProject : subProjectList) {
            counter += getTotalProjectsInTree(subProject);
        }
        assert counter >= ZERO;
        return counter;
    }

    /**
     * Gets the total number of tasks in the tree structure.
     *
     * @param parentWork parent work
     *
     * @return number of tasks from parent
     *
     * @throws WrongParamsException precondition of params
     */
    private int getTotalTasksInTree(final Work parentWork)
            throws WrongParamsException {
        if (parentWork == null) {
            throw new WrongParamsException();
        }
        if (parentWork instanceof Task) {
            return ONE;
        }
        List<Work> subProjectList = ((Project) parentWork).getSubWorkList();
        int counter = ZERO;
        if (subProjectList.size() == ZERO) {
            return counter;
        }
        for (Work subProject : subProjectList) {
            counter += getTotalTasksInTree(subProject);
        }
        assert counter >= ZERO;
        return counter;
    }

    /**
     * Creates a table with each task.
     *
     * @param rootProject root project of the application
     *
     * @return table with tasks
     *
     * @throws WrongParamsException if params are not correct
     */
    private Table createTaskTable(final Project rootProject)
            throws WrongParamsException {
        if (rootProject == null) {
            throw new WrongParamsException();
        }
        List<Work> subProjectList = rootProject.getSubWorkList();
        Table subTaskTable = new Table(getTotalTasksInTree(
                rootProject) + ONE,
                FIVE);

        subTaskTable.setPosition(ZERO, ZERO, "Task Name:  ");
        subTaskTable.setPosition(ZERO, ONE, "Start Date:  ");
        subTaskTable.setPosition(ZERO, TWO, "End Date:  ");
        subTaskTable.setPosition(ZERO, THREE, "Total Time:  ");
        subTaskTable.setPosition(ZERO, FOUR, "Project:  ");

        ReportTableOperation taskVisitor =
                new ReportTableOperation(subTaskTable);
        for (Work subProject : subProjectList) {
            if (subProject instanceof Task) {
                subProject.accept(
                        taskVisitor,
                        getStartDate(),
                        getEndDate());
            } else {
                taskVisitor = addBranchTaskToTable(
                        subProject,
                        taskVisitor);
            }
        }
        assert taskVisitor.getTable() != null;
        return taskVisitor.getTable();
    }

    /**
     * Adds a task to the table.
     *
     * @param parentWork parent work
     * @param visitor visitor to operate with
     *
     * @return visitor to get the table from
     *
     * @throws WrongParamsException precondition of params
     */
    private ReportTableOperation addBranchTaskToTable(
            final Work parentWork,
            ReportTableOperation visitor)  throws WrongParamsException {
        if (parentWork == null || visitor == null) {
            throw new WrongParamsException();
        }
        List<Work> subProjectList = ((Project) parentWork).getSubWorkList();
        if (subProjectList.size() == ZERO) {
            return visitor;
        }
        for (Work subProject : subProjectList) {
            if (subProject instanceof Project) {
                visitor = addBranchTaskToTable(subProject, visitor);
            } else {
                subProject.accept(
                        visitor,
                        getStartDate(),
                        getEndDate());
            }
        }
        return visitor;
    }

    /**
     * Creates table of period.
     *
     * @param rootProject root project
     *
     * @return table with tasks and projects
     *
     * @throws WrongParamsException precondition of params
     */
    public Table createPeriodTable(final Project rootProject)
            throws WrongParamsException {
        if (rootProject == null) {
            throw new WrongParamsException();
        }
        List<Work> subProjectList = rootProject.getSubWorkList();
        Table periodTable = new Table(
                getTotalPeriodsInTree(rootProject) + ONE,
                FIVE + ONE);

        periodTable.setPosition(ZERO, ZERO, "Project Name:  ");
        periodTable.setPosition(ZERO, ONE, "Task Name:  ");
        periodTable.setPosition(ZERO, TWO, "Period:  ");
        periodTable.setPosition(ZERO, THREE, "Start Date:  ");
        periodTable.setPosition(ZERO, FOUR, "End Date:  ");
        periodTable.setPosition(ZERO, FIVE, "Total Time:  ");

        ReportTableOperation visitor = new ReportTableOperation(periodTable);
        for (Work subProject : subProjectList) {
            if (subProject instanceof Task) {
                visitor = addPeriodsToTable(subProject, visitor);
            } else {
                visitor = searchTasksInTree(subProject, visitor);
            }
        }
        assert visitor.getTable() != null;
        return visitor.getTable();
    }

    /**
     * Search for tasks in tree.
     *
     * @param parentWork parent to search
     * @param visitor visitor to operate with
     *
     * @return table operation to get table from
     *
     * @throws WrongParamsException precondition of params
     */
    private ReportTableOperation searchTasksInTree(
            final Work parentWork,
            ReportTableOperation visitor) throws WrongParamsException {
        if (parentWork == null || visitor == null) {
            throw new WrongParamsException();
        }
        if (parentWork instanceof Task) {
            visitor = addPeriodsToTable(parentWork, visitor);
            return visitor;
        }
        List<Work> subProjectList = ((Project) parentWork).getSubWorkList();
        for (Work subProject : subProjectList) {
            visitor = searchTasksInTree(subProject, visitor);
        }
        return visitor;
    }

    /**
     * Adds periods to the table.
     *
     * @param parentWork parent work
     * @param visitor visitor to operate with
     *
     * @return table operation
     *
     * @throws WrongParamsException precondition of params
     */
    private ReportTableOperation addPeriodsToTable(
            final Work parentWork,
            final ReportTableOperation visitor) throws WrongParamsException {
        if (parentWork == null || visitor == null) {
            throw new WrongParamsException();
        }
        List<Period> periods = ((Task) parentWork).getPeriodList();
        if (periods.size() == ZERO) {
            return visitor;
        }
        int id = ONE;
        for (Period period : periods) {
            period.accept(
                    visitor,
                    ((Task) parentWork),
                    getStartDate(),
                    getEndDate());
            visitor.setValueCurrentRow(TWO, "" + id + "  ");
            id++;
        }
        return visitor;
    }

    /**
     * Gets total period in tree structure.
     *
     * @param parentWork parent of work
     *
     * @return number of periods
     *
     * @throws WrongParamsException precondition of params
     */
    private int getTotalPeriodsInTree(final Work parentWork)
            throws WrongParamsException {
        if (parentWork == null) {
            throw new WrongParamsException();
        }
        if (parentWork instanceof Task) {
            return ((Task) parentWork).getPeriodList().size();
        }
        List<Work> subProjectList = ((Project) parentWork).getSubWorkList();
        int counter = ZERO;
        for (Work subProject : subProjectList) {
            counter += getTotalPeriodsInTree(subProject);
        }
        assert counter >= ZERO;
        return counter;
    }
}
