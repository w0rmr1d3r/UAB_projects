package model;

public class DayForecast {	
	private String event;
	private Temperature temperature;	
	
	public DayForecast(String event, int temperature, int scale) {
		this.event = event;
		this.temperature = new Temperature(temperature, scale);
	}
	
	public Temperature getTemperature() {
		return temperature;
	}
	
	public String getEvent(){	
		return event;
	}
}
