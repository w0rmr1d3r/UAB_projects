package controller;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.annotation.PostConstruct;
import model.AdderSingleton2Bean;

/**
 * AdderSingleton2BeanController - eager
 * @author ramonguimera, davidcuadrado
 *
 */
@Singleton
@Startup
public class AdderSingleton2BeanController {

    @EJB AdderSingleton2Bean adder1;
    @EJB AdderSingleton2Bean adder2;
    
    /**
     * Executes tests
     */
    @PostConstruct
    public void init() {
        System.out.println("value adder1 sing eager " + adder1.getValue());
        adder1.add();
        adder1.add();
        System.out.println("value adder1 sing eager " + adder1.getValue());
        
        System.out.println("value adder2 sing eager " + adder2.getValue());
        adder2.add();
        adder2.add();
        System.out.println("value adder2 sing eager " + adder2.getValue());
        
        System.out.println("value adder1 sing eager " + adder1.getValue());
        adder1.add();
        adder1.add();
        System.out.println("value adder1 sing eager " + adder1.getValue());
    }
}
