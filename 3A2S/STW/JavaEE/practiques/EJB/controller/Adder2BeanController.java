package controller;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.annotation.PostConstruct;
import model.Adder2Bean;

/**
 * Adder2BeanController - eager
 * @author ramonguimera, davidcuadrado
 *
 */
@Singleton
@Startup
public class Adder2BeanController {

    @EJB Adder2Bean adder1;
    @EJB Adder2Bean adder2;
    
    /**
     * Executes tests
     */
    @PostConstruct
    public void init() {
        
        System.out.println("value adder1 2Bean " + adder1.getValue());
        adder1.add();
        adder1.add();
        System.out.println("value adder1 2Bean " + adder1.getValue());
        
        System.out.println("value adder2 2Bean " + adder2.getValue());
        adder2.add();
        adder2.add();
        System.out.println("value adder2 2Bean " + adder2.getValue());
         
        System.out.println("value adder1 2Bean " + adder1.getValue());
        adder1.add();
        adder1.add();
        System.out.println("value adder1 2Bean " + adder1.getValue());
    }
}
