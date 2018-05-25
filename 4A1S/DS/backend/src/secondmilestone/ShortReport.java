package secondmilestone;

/*
 * Imported necessary libraries.
 */
import java.util.Date;
import java.util.List;
import firstmilestone.Project;
import firstmilestone.Saver;
import firstmilestone.TimePrinter;
import firstmilestone.Work;
import firstmilestone.WrongParamsException;
import firstmilestone.htmlSaver;
import firstmilestone.txtSaver;

/**
 * Report with low specifications.
 *
 * @author Ramon 1400214, David 1391968
 *
 */
public class ShortReport extends Report {

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
     * Number ten, 10.
     */
    private static final int TEN = 10;

    /**
     * Constructor.
     *
     * @param startDate start date
     * @param endDate end date
     * @param rootProject root project of the application
     *
     * @throws WrongParamsException precondition of params
     */
    public ShortReport(
            final Date startDate,
            final Date endDate,
            final Project rootProject) throws WrongParamsException {

        super(startDate, endDate);

        if (rootProject == null) {
            throw new WrongParamsException();
        }

        addElement(new Line());
        addElement(new Title("Informe Breu"));
        addElement(new Line());
        addElement(new SubTitle("Periode"));
        addElement(createPeriodTable(rootProject));
        addElement(new Line());
        addElement(new SubTitle("Projectes Arrel"));
        addElement(createProjectsTable(rootProject));
        addElement(new Line());
        addElement(new Paragraph("TimeTracker v2.0"));

        assert this.elements.size() == TEN;
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
    public final void printPlainText() throws WrongParamsException {
        this.saver         = new txtSaver();
        String textInforme = "";

        for (Element element : getElements()) {
            textInforme += element.getText() + "\r\n"; // Windows
            //textInforme += element.getText() + "\n"; // Linux
        }

        this.saver.save(textInforme);
    }

    /**
     * Creates the period table, which informs of the report data.
     *
     * @param rootProject root project
     *
     * @return table containing report data
     *
     * @throws WrongParamsException precondition of params
     */
    private Table createPeriodTable(final Project rootProject)
            throws WrongParamsException {
        if (rootProject == null) {
            throw new WrongParamsException();
        }

        Table periodTable = new Table(FOUR, TWO);
        periodTable.setPosition(ZERO, ONE, "Date");
        periodTable.setPosition(ONE, ZERO, "Start Date  ");
        periodTable.setPosition(ONE, ONE, printStartDate());
        periodTable.setPosition(TWO, ZERO, "End Date  ");
        periodTable.setPosition(TWO, ONE, printEndDate());
        periodTable.setPosition(THREE, ZERO, "Report Date  ");
        periodTable.setPosition(THREE, ONE,
                TimePrinter.getInstance().printDate(new Date())
                );

        assert periodTable != null;
        return periodTable;
    }

    /**
     * Creates the projects table.
     *
     * @param rootProject root project of the application
     *
     * @return table containing all root son's projects
     *
     * @throws WrongParamsException precondition of params
     */
    private Table createProjectsTable(final Project rootProject)
            throws WrongParamsException {
        if (rootProject == null
                || rootProject.getSubWorkList() == null
                || rootProject.getSubWorkList().size() <= ZERO) {
            throw new WrongParamsException();
        }

        List<Work> subProjectList = rootProject.getSubWorkList();
        int projectsNumber        = rootProject.getSubWorkList().size();
        Table projectTable        = new Table(projectsNumber + ONE, FOUR);

        projectTable.setPosition(ZERO, ZERO, "Project Name  ");
        projectTable.setPosition(ZERO, ONE, "  Start Date  ");
        projectTable.setPosition(ZERO, TWO, "  End Date  ");
        projectTable.setPosition(ZERO, THREE, "  Total Time");

        ReportTableOperation visitor = new ReportTableOperation(projectTable);

        for (Work subProject : subProjectList) {
            subProject.accept(visitor, getStartDate(), getEndDate());
        }

        assert visitor.getTable() != null;
        return visitor.getTable();
    }
}
