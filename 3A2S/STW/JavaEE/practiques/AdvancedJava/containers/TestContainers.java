package containers;

import java.util.HashMap;
import java.util.List;

/**
 * Class TestContainers
 * @author ramonguimera, davidcuadrado
 *
 */
public class TestContainers {

    /**
     * Constructor
     */
    public TestContainers() {}
    
    /**
     * Prints a fruit list
     * @param fruits list of fruits
     */
    public static void printFruitsList(List<? extends Fruit> fruits) {
        System.out.println(fruits);
    }
    
    /**
     * Prints a map
     * @param fruitsMap map composed of string and list of fruits
     */
    public static void printFruitsMap(
            HashMap<String, List<? extends Fruit>> fruitsMap) {
        System.out.println(fruitsMap);
    }
    
    /**
     * Executes package tests
     * @param args args
     */
    public static void main(String[] args) {  
        List<? extends Fruit> fruitList = FruitFactory.generateAppleArrayList();
        HashMap<String, List<? extends Fruit>> hashMap = FruitFactory.generateFruitsMap();
        
        printFruitsList(fruitList);
        printFruitsMap(hashMap);
    }
}
