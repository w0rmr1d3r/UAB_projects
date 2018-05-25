package secondmilestone;

/*
 * Imported necessary libraries
 */
import firstmilestone.WrongParamsException;

/**
 * Interface to implement to visit Elements.
 *
 * @author Ramon 1400214, David 1391968
 *
 */
public interface VisitorHTML {

    /**
     * Visits element Line.
     *
     * @param line line to visit
     *
     * @throws WrongParamsException if params are not correct
     */
    void visit(Line line) throws WrongParamsException;

    /**
     * Visits element Title.
     *
     * @param title title to visit
     *
     * @throws WrongParamsException if params are not correct
     */
    void visit(Title title) throws WrongParamsException;

    /**
     * Visits element subTitle.
     *
     * @param subTitle subTitle to visit
     *
     * @throws WrongParamsException if params are not correct
     */
    void visit(SubTitle subTitle) throws WrongParamsException;

    /**
     * Visits element Paragraph.
     *
     * @param paragraph paragraph to visit
     *
     * @throws WrongParamsException if params are not correct
     */
    void visit(Paragraph paragraph) throws WrongParamsException;

    /**
     * Visits element Table.
     *
     * @param table table to visit
     *
     * @throws WrongParamsException if params are not correct
     */
    void visit(Table table) throws WrongParamsException;

    /**
     * Visits element Footer.
     *
     * @param footer footer to visit
     *
     * @throws WrongParamsException if params are not correct
     */
    void visit(Footer footer) throws WrongParamsException;
}
