package secondmilestone;

/*
 * Imported necessary libraries
 */
import firstmilestone.WrongParamsException;

/**
 * Line of a report, equals to a <hr> from html.
 *
 * @author Ramon 1400214, David 1391968
 *
 */
public class Line extends Element {

    /**
     * Constructor.
     *
     * @throws WrongParamsException if params are not correct
     */
    public Line() throws WrongParamsException {
        super("--------------------------------------------------------------------------------");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(final VisitorHTML visitor) throws WrongParamsException {
        if (visitor == null) {
            throw new WrongParamsException();
        }
        visitor.visit(this);
    }
}
