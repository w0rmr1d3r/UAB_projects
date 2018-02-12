package controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import model.AlertDB;

//TODO
@ManagedBean(name="alertController")
public class AlertController {

	//TODO
	@EJB AlertDB alertdb;
	@EJB ServerEndPoint serverEndPoint;
	
	@PostConstruct
	private void init() {
		this.message = this.alertdb.getAlertType().get(0);
	}
	
	//Store in this attribute the alert to be sent 
	private String message;
	
	/**
	 * @return The list of the alert types in the database.
	 */
	public List<String>getAlertType() {
		//TODO
		return alertdb.getAlertType();
		//TODO remove return null 
		//return null;
	}
	
	public String getMessage() {
		//TODO 
		return this.message;
		//TODO remove return null
		//return null;
	}

	public void setMessage(String message) {
		//TODO
		this.message = message;
	}

	/**
	 * Sends the alert in the attribute message, using the ServerEndPoint.
	 * @return
	 */
	public String triggerAlert() {
		//TODO
		serverEndPoint.triggerAlert(this.message);
		return "";
		//TODO remove return null
		//return null;
	}
	
}
