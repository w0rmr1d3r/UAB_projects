package controller;

import java.io.IOException;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import model.OperatorsBean;
import view.OperatorLoginBean;

@ManagedBean(name="operatorlogincontroller")
@Singleton
public class OperatorLoginController {
    
    @ManagedProperty(value="#{operatorlogin}") private OperatorLoginBean oplb;
    @EJB private OperatorsBean opb;
    @ManagedProperty(value = "#{operatorchatcontroller}") private OperatorChatController occ;
    private String errMsg = "";
    
    public void setOplb(OperatorLoginBean oplb) {
        this.oplb = oplb;
    }
    
    public void setOcc(OperatorChatController occ) {
        this.occ = occ;
    }
    
    public String validateOperator() throws IOException {
        if(oplb.getLogin() != null && oplb.getPasswd() != null) {
            oplb.setChecked(true);
            if(opb.validateOperator(oplb.getLogin(), oplb.getPasswd())) { 
                oplb.setLogged(true);
                opb.getOperatorByLogin(oplb.getLogin()).setOnline();
                oplb.setOperatorId(opb.getOperatorByLogin(oplb.getLogin()).getId());
                occ.setOperator(opb.getOperatorByLogin(oplb.getLogin()));
            
                return "operatorChat.xhtml?faces-redirect=true";
                
            } else {
                this.errMsg = "Incorrect User or Password";
            }
        }
        return "";
   }
   
   public String getErrMsg(){
       return this.errMsg;
   }
}
