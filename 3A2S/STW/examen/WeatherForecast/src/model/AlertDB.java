package model;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Singleton;

@Singleton
public class AlertDB {
	private List<String> alertType;
	
	public AlertDB(){
		this.alertType = new ArrayList<String>();
		this.alertType.add("Heavy Wind");
		this.alertType.add("Thunder shower");
		this.alertType.add("High Temperatures");
		this.alertType.add("Heavy Snow");
	}

	public List<String> getAlertType() {
		return alertType;
	}
		
}
