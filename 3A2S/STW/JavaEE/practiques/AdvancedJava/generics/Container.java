package generics;

/**
 * Class container
 * @author ramonguimera, davidcuadrado
 *
 * @param <T> type of this container
 */
public class Container<T> {
	
    private T element;
	private T nextElement;
	
	/**
	 * Constructor
	 */
	public Container() {
	    this.element     = null;
	    this.nextElement = null;
	}
	
	/**
	 * Adds an element
	 * @param t element to add
	 */
	public void addElement(final T t) {
		this.element = t;
	}
	
	/**
	 * Adds the next element
	 * @param t next element to add
	 */
	
	public void addNextElement(T t) {
	   this.nextElement = t;
	}
	
	/**
	 * Gets current element
	 * @return current element of type T
	 */
	public T getElement() {
		return this.element;
	}
	
	/**
	 * Gets next element
	 * @return next element of type T
	 */
	public T getNextElement() {
		return this.nextElement;
	}
	
	/**
	 * Prints the elements
	 */
	@Override
	public String toString() {
        try {
            return element.toString() + " " + nextElement.toString();
        } catch(NullPointerException e) {
            return element.toString();
        }       
	}
}
