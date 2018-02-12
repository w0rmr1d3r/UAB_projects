package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;

@Singleton
public class DilbertSaysManager {

    @EJB DilbertSaysDB dilbertSaysDB;
    
    private static Random random;
    private List<String> says;
    private int numberOfDilbertSays;
    
    @PostConstruct
    private void init() {
        random = new Random();
        this.says = new ArrayList<>();
        this.says = this.dilbertSaysDB.getSays();
        this.numberOfDilbertSays = 0;
    }
    
    private static Random getRandom() {
        return random;
    }
    
    public String getRandomSay() {
        this.numberOfDilbertSays += 1;
        return this.says.get(getRandom().nextInt(this.says.size()));
    }
}
