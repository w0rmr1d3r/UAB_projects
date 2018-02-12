package controller;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.annotation.PostConstruct;
import model.AdderSingletonBean;

/**
 * AdderSingletonBeanController - eager
 * @author ramonguimera, davidcuadrado
 *
 */
@Singleton
@Startup
public class AdderSingletonBeanController {

    @EJB AdderSingletonBean adder1;
    @EJB AdderSingletonBean adder2;
    
    /**
     * Executes tests
     */
    @PostConstruct
    public void init() {
        System.out.println("value adder1 sing lazy " + adder1.getValue());
        adder1.add();
        adder1.add();
        System.out.println("value adder1 sing lazy " + adder1.getValue());
        
        System.out.println("value adder2 sing lazy " + adder2.getValue());
        adder2.add();
        adder2.add();
        System.out.println("value adder2 sing lazy " + adder2.getValue());
        
        System.out.println("value adder1 sing lazy " + adder1.getValue());
        adder1.add();
        adder1.add();
        System.out.println("value adder1 sing lazy " + adder1.getValue());
    }
}
