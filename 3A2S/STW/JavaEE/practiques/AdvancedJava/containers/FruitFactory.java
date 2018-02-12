package containers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class FruitFactory
 * @author ramonguimera, davidcuadrado
 *
 */
public class FruitFactory {

    /**
     * Constructor
     */
    public FruitFactory() {}
    
    /**
     * Generates arraylist of apple
     * @return List of apples
     */
    public static List<Apple> generateAppleArrayList() {
        List<Apple> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add(new Apple());   
        }
        return list;
    }
    
    /**
     * Generates arraylist of orange
     * @return List of oranges
     */
    public static List<Orange> generateOrangeArrayList() {
        List<Orange> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add(new Orange());   
        }
        return list;
    }
    
    /**
     * Generates Map of Fruits
     * @return Map of fruits and string indicator
     */
    public static HashMap<String, List<? extends Fruit>> generateFruitsMap() {
        HashMap<String, List<? extends Fruit>> hashMap =
                new HashMap<String, List<? extends Fruit>>();
        
        hashMap.put("apples", generateAppleArrayList());
        hashMap.put("oranges", generateOrangeArrayList());
        
        return hashMap;
    }
}
