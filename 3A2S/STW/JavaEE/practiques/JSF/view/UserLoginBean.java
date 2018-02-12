package view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="userlogin")
@SessionScoped
public class UserLoginBean {

    private String login;
    private String passwd;
    private boolean logged;
    
    public UserLoginBean() {
        this.login  = "";
        this.passwd = "";
        this.logged = false;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswd() {
        return this.passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
    
    public boolean isLogged() {
        return this.logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }
}

