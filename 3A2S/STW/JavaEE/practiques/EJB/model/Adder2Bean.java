package model;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;

/**
 * Adder2Bean
 * @author ramonguimera, davidcuadrado
 *
 */
@Stateless
public class Adder2Bean {

    private int value = 0;
    
    /**
     * Prints itself
     */
    @PostConstruct
    public void init() {
        System.out.println(System.currentTimeMillis() + " - Creat el Bean2: " + this);
    }

    /**
     * Prints itself
     */
    @PreDestroy
    public void destroy() {
        System.out.println(System.currentTimeMillis() + " - Destruint el Bean2: " + this);
    }
    
    /**
     * Gets value
     * @return value
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Sets value
     * @param value new value
     */
    public void setValue(int value) {
        this.value = value;
    }
    
    /**
     * Increments value
     */
    public void add() {
        this.value += 1;
    }

    /**
     * Resets value
     */
    public void reset() {
        this.value = 0;
    }
}
