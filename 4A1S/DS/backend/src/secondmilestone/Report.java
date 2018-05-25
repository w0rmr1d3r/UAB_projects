package secondmilestone;

/*
 * Imported necessary libraries.
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import firstmilestone.TimePrinter;
import firstmilestone.WrongParamsException;

/**
 * Class report.
 *
 * @author Ramon 1400214, David 1391968
 *
 */
public abstract class Report {

    /**
     * Start date of the report.
     */
    private Date startDate;

    /**
     * End date of the report.
     */
    private Date endDate;

    /**
     * List of elements inside the report.
     */
    protected List<Element> elements;

    /**
     * Constructor.
     *
     * @param newStartDate start date of the report
     * @param newEndDate end date of the report
     *
     * @throws WrongParamsException if params are not correct
     */
    public Report(final Date newStartDate, final Date newEndDate)
            throws WrongParamsException {
        if (newStartDate == null
                || newEndDate == null
                || (newStartDate.getTime() - newEndDate.getTime() > 0)) {
            throw new WrongParamsException();
        }
        this.startDate = newStartDate;
        this.endDate   = newEndDate;
        this.elements  = new ArrayList<Element>();

        assert this.startDate != null;
        assert this.endDate != null;
        assert (this.startDate.getTime() - this.endDate.getTime() <= 0);
        assert this.elements != null;
        assert this.elements.size() >= 0;
    }

    /**
     * Prints to plain text.
     *
     * @throws WrongParamsException if params are not correct
     */
    public abstract void printPlainText() throws WrongParamsException;

    /**
     * Print to html.
     *
     * @throws WrongParamsException if params are not correct
     */
    public abstract void printHTML() throws WrongParamsException;

    /**
     * Adds an element to element list.
     *
     * @param element element to add to the report
     *
     * @throws WrongParamsException if params are not correct
     */
    public void addElement(final Element element) throws WrongParamsException {
        if (element == null
                || this.elements == null) {
            throw new WrongParamsException();
        }
        int preSize = this.elements.size();
        this.elements.add(element);
        assert this.elements.size() >= 1;
        assert preSize < this.elements.size();
    }

    /**
     * Gets elements.
     *
     * @return returns the list of elements
     */
    public List<Element> getElements() {
        return this.elements;
    }

    /**
     * Gets start date.
     *
     * @return return the report start date
     */
    public Date getStartDate() {
        return this.startDate;
    }

    /**
     * Gets end date.
     *
     * @return returns the report end date
     */
    public Date getEndDate() {
        return this.endDate;
    }

    /**
     * Prints start date as string.
     *
     * @return prints the starting date of the report
     */
    public String printStartDate() {
        return TimePrinter.getInstance().printDate(this.startDate);
    }

    /**
     * Prints end date as string.
     *
     * @return prints the starting date of the report
     */
    public String printEndDate() {
        return TimePrinter.getInstance().printDate(this.endDate);
    }
}
