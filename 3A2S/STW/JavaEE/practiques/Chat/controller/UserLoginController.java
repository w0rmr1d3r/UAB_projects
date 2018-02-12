package controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;

import model.CatalogBean;
import model.ConversationsManager;
import model.Product;
import view.UserLoginBean;

@ManagedBean(name="userlogincontroller")
@SessionScoped
public class UserLoginController {

	@EJB private CatalogBean catalog;
    @EJB private ConversationsManager conversationsManager;
	@ManagedProperty(value="#{userlogin}") UserLoginBean user;
	private String defaultCategory;
	private String[] categories;
	private static int lastUserId = 0;
	private String errMsg = "";

    @PostConstruct
	private void init() {
		this.categories = this.catalog.getCategories();
		this.defaultCategory = this.categories[0];
		this.user.setSelectedCategory(defaultCategory);
	}

	public String getDefaultCategory() {
		return defaultCategory;
	}

	public String[] getCategories() {
		return categories;
	}

	public void setUser(UserLoginBean user) {
		this.user = user;
	}
	
	public List<Product> getProducts() {
		return this.catalog.getCategoryProducts(defaultCategory);
	}
	
	public void categoryChanged(ValueChangeEvent e) {
		this.user.setSelectedCategory(e.getNewValue().toString());
		this.defaultCategory = e.getNewValue().toString();
	}
	
	public String createConversation() {
        if(user.getName().isEmpty()){
        	errMsg = "Name must not be empty";
        	return "";
        }
        System.out.println(user.getName().length());
        try {
        	user.setUserId(generateUserId());
            conversationsManager.createConversation(
                user.getUserId(), 
                user.getSelectedCategory(), 
                user.getSelectedProduct(), 
                user.getSubject(), 
                user.getName()
                    );
            return "userChat.xhtml?faces-redirect=true";
        } catch (OperatorNotFoundException e) {
            errMsg = "No operator available!";
            return "";
        }
	}
	
	private static synchronized int generateUserId() {
        lastUserId += 1;
        return lastUserId;
	}
	
	public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
