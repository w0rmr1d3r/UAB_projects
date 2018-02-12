package anonymous;

/**
 * Class TestAnonymous
 * Tests anonymous classes
 * @author ramonguimera, davidcuadrado
 *
 */
public class TestAnonymous {

    private Counter counterByOne;
    private Counter counterByTwo;
   
    /**
     * Constructor
     * Creates anon classes for the attributes
     */
    public TestAnonymous() {
        this.counterByOne = new Counter() {
            public int increment(int value) {
                return value + 1;
            }
        };
        
        this.counterByTwo = new Counter() {
            public int increment(int value) {
                return value + 2;
            }
        };
    }
    
    /**
     * Gets counterByOne
     * @return Counter counterByOne
     */
    public Counter getCounterByOne() {
        return this.counterByOne;
    }
    
    /**
     * Gets counterByTwo
     * @return Counter counterByTwo
     */
    public Counter getCounterByTwo() {
        return this.counterByTwo;
    }
    
    /**
     * Tests anon package
     * @param args args
     */
    public static void main(String[] args) {
        TestAnonymous ta = new TestAnonymous();
        System.out.println(ta.getCounterByOne().increment(2));
        System.out.println(ta.getCounterByTwo().increment(2));
    }
}
