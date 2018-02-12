package generics;

/**
 * Class TestGenereics
 * To test Container and TwoTuple
 * @author ramonguimera, davidcuadrado
 *
 */
public class TestGenerics {

    /**
     * Executes tests for Container
     */
    public static void testContainer() {
    	Container<Integer> c = new Container<Integer>();
        Container<String> cs = new Container<String>();

        c.addElement(1);
        c.addNextElement(2);
            
        cs.addElement("hola");
        cs.addNextElement("que tal?");
            
        System.out.println(c.toString());
        System.out.println(cs.toString());
    }
    
    /**
     * Executes tests for TwoTuple
     */
    public static void testTwoTuple() {
        Container<String> cs = new Container<String>();
        cs.addElement("hola");
        cs.addNextElement("que tal?");
        
        TwoTuple<Integer, String> ttOne = new TwoTuple<>(1, "David");
        TwoTuple<Float, Container<String>> ttTwo = new TwoTuple<>(3f, cs);
        
        System.out.println(ttOne.toString());
        System.out.println(ttTwo.toString());
    }

    /**
     * Executes tests of this package
     * @param args args
     */
    public static void main(String[] args) {
            testContainer();
            testTwoTuple();
    }       
}
