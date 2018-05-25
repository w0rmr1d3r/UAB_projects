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
 * Saves an loads objects into .txt files.
 *
 * @author Ramon 1400214, David 1391968
 *
 */
public class txtSaver extends Saver {

    /**
     * Saver constructor.
     * It only creates the object.
     */
    public txtSaver() {
        MyLogger.info("txtSaver created");
    }

    /**
     * Saves an object into a .txt file.
     *
     * @param string object to be saved
     *
     * @throws WrongParamsException precondition of params
     */
    @Override
    public final void save(final Object string) throws WrongParamsException {
        if (string == null) {
            throw new WrongParamsException("Null params");
        }

        try {
            Writer writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream("informe.txt"),
                            "utf-8")
                    );
            writer.write((String) string);
            writer.close();
            MyLogger.info("Saved object string into .txt file");

        } catch (UnsupportedEncodingException e) {
            MyLogger.severe("UnsupportedEncodingException txtSaver");
            MyLogger.severe(e.getMessage());

        } catch (FileNotFoundException e) {
            MyLogger.severe("FileNotFoundException txtSaver");
            MyLogger.severe(e.getMessage());

        } catch (IOException e) {
            MyLogger.severe("IOException txtSaver");
            MyLogger.severe(e.getMessage());
        }
    }

    /**
     * Loads an object from a .txt file.
     *
     * @return object loaded
     */
    @Override
    public final Object load() {
        return null;
    }
}
