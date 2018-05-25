package secondmilestone;

/*
 * Imported necessary libraries
 */
import firstmilestone.WrongParamsException;

/**
 * Element of type Title.
 *
 * @author Ramon 1400214, David 1391968
 *
 */
public class Title extends Element {

    /**
     * Constructor.
     *
     * @param text text which the element is made of
     *
     * @throws WrongParamsException if params are not correct
     */
    public Title(final String text) throws WrongParamsException {
        super(text);
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
