package containers;

/**
 * Class Fruit
 * @author ramonguimera, davidcuadrado
 *
 */
public class Fruit {

    private static int counter = 0;
    private int id;
    
    /**
     * Constructor
     */
    public Fruit() {
        this.id = counter + 1;
        counter = this.id;
    }
    
    /**
     * Prints this id
     */
    @Override
    public String toString() {
        return "" + this.id;
    }
}
