package model;

import java.util.HashMap;

import javax.ejb.Singleton;

@Singleton
public class WeekForecast {
	
	private HashMap<String, DayForecast> forecast;
	
	public WeekForecast(){
		this.forecast = new HashMap<String, DayForecast>();
		this.forecast.put("Monday",    new DayForecast("Sunny",  30, Temperature.CELSIUS_SCALE));
		this.forecast.put("Tuesday",   new DayForecast("Clowdy", 25, Temperature.CELSIUS_SCALE));
		this.forecast.put("Wednesday", new DayForecast("Rainy",  20, Temperature.CELSIUS_SCALE));
		this.forecast.put("Thursday",  new DayForecast("Storms", 15, Temperature.CELSIUS_SCALE));
		this.forecast.put("Friday",    new DayForecast("Rainy",  16, Temperature.CELSIUS_SCALE));
		this.forecast.put("Saturday",  new DayForecast("Clowdy", 21, Temperature.CELSIUS_SCALE));
		this.forecast.put("Sunday",    new DayForecast("Sunny",  29, Temperature.CELSIUS_SCALE));
	}

	public HashMap<String, DayForecast> getForecast() {
		return this.forecast;
	}
}
