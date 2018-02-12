package model;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateful;

/**
 * AdderBean
 * @author ramonguimera, davidcuadrado
 *
 */
@Stateful
public class AdderBean {

    private int value = 0;
    
    /**
     * Prints itself
     */
    @PostConstruct
    public void init() {
        System.out.println(System.currentTimeMillis() + " - Creat el Bean: " + this);
    }

    /**
     * Prints itself
     */
    @PreDestroy
    public void destroy() {
        System.out.println(System.currentTimeMillis() + " - Destruint el Bean: " + this);
    }
    
    /**
     * Returns value
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
     * Increments value in 1
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
