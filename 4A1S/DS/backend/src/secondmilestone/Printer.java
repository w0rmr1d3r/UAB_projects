package secondmilestone;

/*
 * Imported necessary libraries
 */
import firstmilestone.WrongParamsException;

/**
 * Class to print elements.
 *
 * @author Ramon 1400214, David 1391968
 *
 */
public final class Printer {

    /**
     * Singleton instance of the class.
     */
    private static Printer instance = null;

    /**
     * Constructor.
     */
    private Printer() {
    }

    /**
     * Gets instance.
     *
     * @return instance of the class
     */
    public static Printer getInstance() {
        if (instance == null) {
            instance = new Printer();
        }
        assert instance != null;
        return instance;
    }

    /**
     * Prints the element.
     *
     * @param element element to print
     *
     * @throws WrongParamsException if params are not correct
     */
    public void print(final Element element) throws WrongParamsException {
        if (element == null) {
            throw new WrongParamsException();
        }
        System.out.println(element.getText());
    }
}
