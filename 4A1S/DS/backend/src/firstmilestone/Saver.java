package firstmilestone;

/**
 * Class saver.
 * Saves and loads objects.
 *
 * @author Ramon 1400214, David 1391968
 *
 */
public abstract class Saver {

    /**
     * Saver constructor.
     * It only creates the object.
     */
    public Saver() {
    }

    /**
     * Saves an object.
     *
     * @param object object to be saved
     *
     * @throws WrongParamsException precondition of params
     */
    public abstract void save(Object object) throws WrongParamsException;

    /**
     * Loads an object from a file.
     *
     * @return object loaded
     */
    public abstract Object load();
}
