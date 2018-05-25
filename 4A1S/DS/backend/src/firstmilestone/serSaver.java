package firstmilestone;

/*
 * Imported necessary libraries
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Class serSaver.
 * Saves and loads objects into .ser archives.
 *
 * @author Ramon 1400214, David 1391968
 *
 */
public class serSaver extends Saver {

    /**
     * Saver constructor.
     * It only creates the object.
     */
    public serSaver() {
        MyLogger.info("serSaver created");
    }

    /**
     * Saves an object into a .ser extension archive.
     *
     * @param object object to be saved
     *
     * @throws WrongParamsException precondition of params
     */
    @Override
    public final void save(final Object object) throws WrongParamsException {
        if (object == null) {
            throw new WrongParamsException("Null params");
        }

        try {
            FileOutputStream fileToWrite = new FileOutputStream("status.ser");
            ObjectOutputStream out       = new ObjectOutputStream(fileToWrite);
            out.writeObject(object);
            out.close();
            fileToWrite.close();
            MyLogger.info("Object saved as a .ser file");

        } catch (FileNotFoundException e) {
            MyLogger.severe("FileNotFoundException at serSaver while loadindg");
            MyLogger.severe(e.getMessage());

        } catch (IOException e) {
            MyLogger.severe("IOException at serSaver while loadindg");
            MyLogger.severe(e.getMessage());
        }
    }

    /**
     * Loads an object from a .ser file.
     *
     * @return object loaded
     */
    @Override
    public final Object load() {
        Object obj = null;

        try {
            FileInputStream fileToRead = new FileInputStream("status.ser");
            ObjectInputStream in       = new ObjectInputStream(fileToRead);
            obj = in.readObject();
            in.close();
            fileToRead.close();
            MyLogger.info("Object loaded from file .ser");
            return obj;

        } catch (ClassNotFoundException e) {
            MyLogger.severe("ClassNotFoundExcept. at serSaver while loadindg");
            MyLogger.severe(e.getMessage());

        } catch (IOException e) {
            MyLogger.severe("IOException at serSaver while loadindg");
            MyLogger.severe(e.getMessage());
        }

        return obj;
    }
}
