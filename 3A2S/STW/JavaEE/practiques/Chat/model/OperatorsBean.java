package model;

import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

@Singleton
public class OperatorsBean {
    
    private HashMap<Integer, Operator> operators;
    
    @PostConstruct
    private void init() {
        this.operators = new HashMap<Integer, Operator>();
        this.operators.put(1, new Operator(1001, "root", "root"));
        this.operators.put(2, new Operator(2002, "admin", "admin"));
    }
    
    public boolean validateOperator(String login, String passwd) {
        for (Operator op : this.operators.values()) {
            if(op.getLogin().compareTo(login.toLowerCase()) == 0 
                    && op.getPasswd().compareTo(passwd) == 0 ) {
            	op.setOnline();
            	return true;
            }
        }
        return false;
    }
    
    public HashMap<Integer, Operator> getOperatorsList() {
        return operators;
    }
    
    public Operator getOperatorByLogin(String login) {
        for (Operator op : this.operators.values()) {
            if(op.getLogin().compareTo(login) == 0) {
                return op;
            }
        }
        return null;
    }
    
    public Operator getOperatorById(int id) {
        for (Operator op : this.operators.values()) {
            if(op.getId() == id) {
                return op;
            }
        }
        return null;
    }
}
