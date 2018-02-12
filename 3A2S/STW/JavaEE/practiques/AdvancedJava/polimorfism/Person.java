package polimorfism;

import java.util.ArrayList;
import java.util.List;

/**
 * Person class
 * Has innerclass Address
 * @author ramonguimera, davidcuadrado
 *
 */
public class Person {
    
    private List<Person.Address> addresses;
    private String name;
    private int age;
    
    /**
     * Constructor
     * @param name Of the person
     * @param age Of the person
     */
    public Person(final String name, final int age) {
        this.name = name;
        this.age  = age;
        this.addresses = new ArrayList<>();
    }
    
    /**
     * Adds an address to this person
     * @param country of this address
     * @param province inside the country
     * @param street name of the street
     * @param number of the street
     */
    public void addAddress(
            final String country, 
            final String province, 
            final String street, 
            final int number) {
        this.addresses.add(new Address(country, province, street, number));
    }
    
    /**
     * Prints every address of this person
     */
    public void printAddresses() {
        for(Address address : this.addresses) {
            System.out.println(address.toString());
        }
    }
    
    /**
     * Prints person
     */
    @Override
    public String toString() {
        return this.name + " " + this.age;
    }
    
    /**
     * Class Address
     * Created as public, but can be protected too
     * @author ramonguimera, davidcuadrado
     *
     */
    public class Address {
        
        private String country;
        private String province;
        private String street;
        private int number;
        
        /**
         * Constructor of address
         * @param country of this address
         * @param province of this address
         * @param street of this address
         * @param number of this address
         */
        public Address(
                final String country, 
                final String province, 
                final String street, 
                final int number) {
            this.country  = country;
            this.province = province;
            this.street = street;
            this.number = number;
        }
        
        /**
         * Prints address
         */
        @Override
        public String toString() {
            return this.street + " " + this.province + " " + this.number + " " + this.country;
        }
    }
}
