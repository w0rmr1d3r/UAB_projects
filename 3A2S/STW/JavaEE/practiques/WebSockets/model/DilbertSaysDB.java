package model;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

@Singleton
public class DilbertSaysDB {

    private List<String> says; 
    
    @PostConstruct
    private void init() {
        this.says = new ArrayList<>(); 
        this.says.add("Engineers like to solve problems. If there are no problems handily avialable, they will create their own problems.");
        this.says.add("An optimist is simply a pessimist with no job experience.");
        this.says.add("When did ignorance become a point of view?");
        this.says.add("Change is good. You go first.");
        this.says.add("I get mail; therefore I am.");
    }
    
    public List<String> getSays() {
        return this.says;
    }
}
