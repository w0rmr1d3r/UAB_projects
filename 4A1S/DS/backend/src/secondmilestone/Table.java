package secondmilestone;

/*
 * Imported necessary libraries.
 */
import java.util.ArrayList;
import firstmilestone.WrongParamsException;

/**
 * Element of type Table.
 *
 * This class was remade from the Taula class made by J. Serrat.
 * So each method does the same as the original class, but remade in English.
 *
 * @author Ramon 1400214, David 1391968
 *
 */
public class Table extends Element {

    /**
     * Number of rows of the table.
     */
    private int rows;

    /**
     * Number of columns of the table.
     */
    private int columns;

    /**
     * Array list to attach data strings.
     */
    private ArrayList table;

    /**
     * Constructor.
     *
     * @param newRows table rows
     * @param newColumns table columns
     *
     * @throws WrongParamsException if params are not correct
     */
    public Table(final int newRows, final int newColumns)
            throws WrongParamsException {
        super("");

        if (newRows <= 0 || newColumns <= 0) {
            throw new WrongParamsException();
        }

        this.rows    = newRows;
        this.columns = newColumns;
        this.table   = new ArrayList<String>();

        for (int i = 0; i < rows; i++) {
            ArrayList row = new ArrayList<String>();
            for (int j = 0; j < columns; j++) {
                row.add(new String());
            }
            this.table.add(row);
        }
    }

    /**
     * Returns ArrayList of table.
     *
     * @return table array list of strings
     */
    public final ArrayList getTable() {
        return this.table;
    }

    /**
     * Returns number of rows.
     *
     * @return rows number of rows.
     */
    public final int getRows() {
        return this.rows;
    }

    /**
     * Return number of columns.
     *
     * @return columns number of columns of the table
     */
    public final int getColumns() {
        return this.columns;
    }

    /**
     * Sets value on position specified.
     *
     * @param row position row
     * @param column position column
     * @param value value to set
     *
     * @throws WrongParamsException if params are not correct
     */
    public final void setPosition(
            final int row,
            final int column,
            final String value) throws WrongParamsException {
        if (row < 0 || column < 0 || value == null) {
            throw new WrongParamsException();
        }
        ((ArrayList) getTable().get(row)).set(column, value);
    }

    /**
     * Gets value on position specified.
     *
     * @param row row position
     * @param column column position
     * @return string position
     *
     * @throws WrongParamsException if params are not correct
     */
    public final String getPosition(final int row, final int column)
            throws WrongParamsException {
        if (row < 0 || column < 0) {
            throw new WrongParamsException();
        }
        return (String) ((ArrayList) getTable().get(row)).get(column);
    }

    /**
     * Gets text from the whole table.
     *
     * @return tableText string made of the whole table strings
     *
     * @throws WrongParamsException if table coordinates are incorrect
     */
    @Override
    public String getText() throws WrongParamsException {
        String tableText = "";

        for (int i = 0; i < getTable().size(); i++) {
            for (int j = 0; j < ((ArrayList) getTable().get(i)).size(); j++) {
                if (getPosition(i, j) != null) {
                    tableText += getPosition(i, j);
                } else {
                    tableText += "";
                }
            }
            tableText += "\r\n"; // Windows
            //tableText += "\n"; // Linux
        }
        return tableText;
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
