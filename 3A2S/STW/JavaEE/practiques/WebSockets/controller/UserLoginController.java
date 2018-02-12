package controller;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext; 

import model.UserManager;
import view.UserLoginBean;

@ManagedBean(name="userlogincontroller")
public class UserLoginController {

    @ManagedProperty(value="#{userlogin}")
    private UserLoginBean user;
    
    @EJB UserManager userManager;
    
    public UserLoginController() {
        
    }
    
    public String loginUser() {
        String nextPage = "notLogged.xhmtl";
        if (userManager.isARegisteredUser(user.getLogin(), user.getPasswd())) {
            user.setLogged(true);
            nextPage = "welcome.xhtml";
        }
        return nextPage;
    }
    
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        user.setLogged(false);
        return "index.xhtml";
    }

    public void setUser(UserLoginBean user) {
        this.user = user;
    }
    
}

