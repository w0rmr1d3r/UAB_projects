package secondmilestone;

/*
 * Imported necessary libraries
 */
import firstmilestone.WrongParamsException;

/**
 * Subtitle is a type of element.
 *
 * @author Ramon 1400214, David 1391968
 *
 */
public class SubTitle extends Element {

    /**
     * Constructor.
     *
     * @param text text which the element is made of
     *
     * @throws WrongParamsException if params are not correct
     */
    public SubTitle(final String text) throws WrongParamsException {
        super(text);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void accept(final VisitorHTML visitor)
            throws WrongParamsException {
        if (visitor == null) {
            throw new WrongParamsException();
        }
        visitor.visit(this);
    }
}
