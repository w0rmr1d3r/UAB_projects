package model;

import java.util.Hashtable;

import javax.ejb.EJB;
import javax.ejb.Singleton;

@Singleton
public class UserManager {

    @EJB UserDB userDB;
    
    public boolean isARegisteredUser(String login, String passwd) {
        
        Hashtable<String, String> users = userDB.getAuthorizedUsers();
        return (users.containsKey(login) && users.get(login).equals(passwd));
    }
}
