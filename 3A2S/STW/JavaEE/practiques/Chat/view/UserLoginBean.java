package view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="userlogin")
@SessionScoped
public class UserLoginBean {

	private String name;
	private String subject;
	private String selectedCategory;
	private String selectedProduct;
	private int userId;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getSelectedCategory() {
		return selectedCategory;
	}
	
	public void setSelectedCategory(String selectedCategory) {
		this.selectedCategory = selectedCategory;
	}
	
	public String getSelectedProduct() {
		return selectedProduct;
	}
	
	public void setSelectedProduct(String selectedProduct) {
		this.selectedProduct = selectedProduct;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
}
