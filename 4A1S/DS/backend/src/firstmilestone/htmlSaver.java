package firstmilestone;

/*
 * Imported necessary libraries
 */
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

/**
 * Class txtSaver.
 * Saves strings into .html files.
 *
 * @author Ramon 1400214, David 1391968
 *
 */
public class htmlSaver extends Saver {

    /**
     * Saver constructor.
     * It only creates the object.
     */
    public htmlSaver() {
        MyLogger.info("htmlSaver created");
    }

    /**
     * Saves an object into a .html file.
     *
     * @param string object to be saved
     *
     * @throws WrongParamsException precondition of params
     */
    @Override
    public final void save(final Object string) throws WrongParamsException {
        if (string == null) {
            throw new WrongParamsException("Params null");
        }

        try {
            Writer writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream("informe.html"),
                            "utf-8")
                    );
            writer.write((String) string);
            writer.close();

        } catch (UnsupportedEncodingException e) {
            MyLogger.severe("UnsupportedEncodingException in htmlSaver");
            MyLogger.severe(e.getMessage());

        } catch (FileNotFoundException e) {
            MyLogger.severe("FileNotFoundException in htmlSaver");
            MyLogger.severe(e.getMessage());

        } catch (IOException e) {
            MyLogger.severe("IOException in htmlSaver");
            MyLogger.severe(e.getMessage());
        }
    }

    /**
     * Loads an object from a .html file.
     *
     * @return Object null
     */
    @Override
    public final Object load() {
        return null;
    }
}
