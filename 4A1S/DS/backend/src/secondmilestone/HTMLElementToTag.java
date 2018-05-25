package secondmilestone;

/*
 * Imported necessary libraries
 */
import firstmilestone.WrongParamsException;

/**
 * Visitor implemented to generate HTML from elements.
 *
 * @author Ramon 1400214, David 1391968
 */
public class HTMLElementToTag implements VisitorHTML {

    /**
     * Web page to create.
     */
    private WebPage webPage;

    /**
     * Number one, 1.
     */
    private static final int ONE = 1;

    /**
     * Number three, 3.
     */
    private static final int THREE = 3;

    /**
     * Number five, 5.
     */
    private static final int FIVE = 5;

    /**
     * Constructor.
     */
    public HTMLElementToTag() {
        webPage = new WebPage();
    }

    /**
     * Gets web page as string.
     *
     * @return webPage as string
     */
    public String getWebPageText() {
        return this.webPage.getText();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Line line) throws WrongParamsException {
        if (line == null) {
            throw new WrongParamsException();
        }
        this.webPage.addLineBreak();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Title title) throws WrongParamsException {
        if (title == null) {
            throw new WrongParamsException();
        }
        this.webPage.addHeader(title.getText(), ONE, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final SubTitle subTitle) throws WrongParamsException {
        if (subTitle == null) {
            throw new WrongParamsException();
        }
        this.webPage.addHeader(subTitle.getText(), THREE, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Paragraph paragraph) throws WrongParamsException {
        if (paragraph == null) {
            throw new WrongParamsException();
        }
        this.webPage.addNormalText(paragraph.getText());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Table table) throws WrongParamsException {
        if (table == null) {
            throw new WrongParamsException();
        }
        this.webPage.afegeixTaula(table.getTable(), false, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Footer footer) throws WrongParamsException {
        if (footer == null) {
            throw new WrongParamsException();
        }
        this.webPage.addHeader(footer.getText(), FIVE, false);
    }
}
