package controller;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.annotation.PostConstruct;
import model.AdderBean;

/**
 * AdderBeanController - eager
 * @author ramonguimera, davidcuadrado
 *
 */
@Singleton
@Startup
public class AdderBeanController {

    @EJB AdderBean adder1;
    @EJB AdderBean adder2;
    
    /**
     * Executes test
     */
    @PostConstruct
    public void init() {
        System.out.println("value adder1 " + adder1.getValue());
        adder1.add();
        adder1.add();
        System.out.println("value adder1 " + adder1.getValue());
        
        System.out.println("value adder2 " + adder2.getValue());
        adder2.add();
        adder2.add();
        System.out.println("value adder2 " + adder2.getValue());
        
        System.out.println("value adder1 " + adder1.getValue());
        adder1.add();
        adder1.add();
        System.out.println("value adder1 " + adder1.getValue());
    }
}
