package secondmilestone;

/*
 * Imported necessary libraries
 */
import firstmilestone.WrongParamsException;

/**
 * Element of the report.
 *
 * @author Ramon 1400214, David 1391968
 *
 */
public abstract class Element {

    /**
     * Text of the element.
     */
    private String text;

    /**
     * Printer to print the element text.
     */
    private static Printer printer;

    /**
     * Constructor.
     *
     * @param newText text which the element is made of
     *
     * @throws WrongParamsException if params are not correct
     */
    public Element(final String newText) throws WrongParamsException {
        if (newText == null) {
            throw new WrongParamsException();
        }
        this.text = newText;
        printer   = Printer.getInstance();
        assert this.text != null;
        assert printer != null;
    }

    /**
     * Getter of the property text.
     *
     * @return Returns the text.
     *
     * @throws WrongParamsException only table element throws this
     */
    public String getText() throws WrongParamsException {
        return this.text;
    }

    /**
     * Prints the element as text.
     *
     * @throws WrongParamsException if params are not correct
     * @throws NullPointerException if printer is null
     */
    public void print() throws WrongParamsException, NullPointerException {
        if (printer == null) {
            throw new NullPointerException("Printer is null");
        }
        printer.print(this);
    }

    /**
     * Accepts a HTML visitor.
     *
     * @param visitor visitor to accept
     *
     * @throws WrongParamsException if params are not correct
     */
    public abstract void accept(VisitorHTML visitor)
            throws WrongParamsException;
}
