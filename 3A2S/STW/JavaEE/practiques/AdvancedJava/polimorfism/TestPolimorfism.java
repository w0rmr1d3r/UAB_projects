package polimorfism;

/**
 * Class TestPolimorfism
 * To test poliformism around the classes of this package
 * @author ramonguimera, davidcuadrado
 *
 */
public class TestPolimorfism {

    /**
     * Constructor
     */
    public TestPolimorfism() {}
    
    /**
     * Prints person
     * @param person to print
     */
    public static void printInformation(final Person person) {
        System.out.println(person);
    }
    
    /**
     * Executes the tests
     * @param args args
     */
    public static void main(String[] args) {
        // 6a, b
        Person lia  = new Person("Lia", 23);
        Student pep = new Student("Pep", 19);
        Person ana  = new Student("Ana", 20);
        Teacher ian = new Teacher("Ian", 25);
        // 6c
        ((Student) ana).addSubject("STW");
        // 6d
        printInformation(lia);
        printInformation(pep);
        printInformation(ana);
        printInformation(ian);
    }
}
