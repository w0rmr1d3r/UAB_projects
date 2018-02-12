package model;

import java.util.Hashtable;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

@Singleton
public class UserDB {

    private Hashtable<String, String> authorizedUsers;
    
    @PostConstruct
    private void init() {
        this.authorizedUsers = new Hashtable<String, String>();
        this.authorizedUsers.put("admin", "admin");
        this.authorizedUsers.put("root", "root");
    }
    
    public Hashtable<String, String> getAuthorizedUsers() {
        return this.authorizedUsers;
    }
}
