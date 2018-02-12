package polimorfism;

/**
 * Class TestPerson
 * To test class person
 * @author ramonguimera, davidcuadrado
 *
 */
public class TestPerson {

    /**
     * Constructor
     */
    public TestPerson() {}
    
    /**
     * Executes tests
     * @param args args
     */
    public static void main(String[] args) {
        // 8a
        Person person = new Person("David", 20);
        // 8b
        person.addAddress("spain", "cat", "rubi street", 1990);
        // 8c
        System.out.println(person.toString());
        // 8d
        person.printAddresses();
        // 8e
        Person.Address address = person.new Address("UK", "london", "the beatles street", 1990);
        // 8f
        System.out.println(address.toString());
    }
}
