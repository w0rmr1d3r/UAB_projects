package controller;

import java.io.IOException;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import model.DilbertSaysManager;
import view.UserLoginBean;

@ManagedBean(name="dilbertcontroller")
public class DilbertSaysController {

    @ManagedProperty(value="#{userlogin}")
    private UserLoginBean user;
    
    @EJB DilbertSaysManager dilbertSaysManager;
    
    private static String notAllowedMessage = "User Not Allowed";
    private String randomSay = "";
    
    public String getRandomSay() 
            throws IOException {
        if (this.user.isLogged()) {
            this.randomSay += this.dilbertSaysManager.getRandomSay();
        } else {
            FacesContext.getCurrentInstance()
                    .getExternalContext().redirect("notLogged.xhtml");
        }
        return this.randomSay;
    }
    
    public void setUser(UserLoginBean user) {
        this.user = user;
    }
    
    public String getNotAllowedMessage() {
        return notAllowedMessage;
    }
}

