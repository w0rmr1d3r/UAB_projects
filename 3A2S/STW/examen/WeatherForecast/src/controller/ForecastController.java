package controller;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import model.DayForecast;
import model.Temperature;
import model.WeekForecast;


//TODO
@SessionScoped
@ManagedBean(name="forecastController")
public class ForecastController {
	
	//TODO	
	@EJB WeekForecast weekforecast;
	private List<DayForecast> weekForecast = null;
	private int displayScale = Temperature.CELSIUS_SCALE;
	
	/**
	 * @return Returns a List with the forecast for all week.
	 */
	public List<DayForecast> getWeekForecast(){
		//TODO
		List<DayForecast> dayForecast = new ArrayList<DayForecast>();
		System.out.println("INFO >>> FCONTR SHOWING DAYS");
		for (DayForecast day : this.weekforecast.getForecast().values()) {
			dayForecast.add(day);
		}
		
		return dayForecast; 
		//TODO remove the return 
		//return null;
	}
		
	public int getDisplayScale() {
		//TODO
		return this.displayScale;
		//TODO remove the return 0
		//return 0;
	}
	
	/**
	 * Return true if the current displayScale is Celcius 
	 * (Temperature.CELSIUS_SCALE)
	 * @return
	 */
	public boolean isCelsius() {
		//TODO
		return (this.displayScale == Temperature.CELSIUS_SCALE);
		//TODO remove the return false
		//return false;
	}
	
	/**
	 * If the displayScale is Celcius switch displayScale to 
	 * Fahrenheit and the other way round.
	 * @return 
	 */
	public String switchScale() {
		
		//TODO
		System.out.println("INFO >>> CALLED SWITCHSCALE");
		if (this.displayScale == Temperature.CELSIUS_SCALE) {
			System.out.println("Escale was C");
			this.displayScale = Temperature.FAHRENHEIT_SCALE;
			System.out.println(this.displayScale);
		} else {
			System.out.println("Escale was F");
			this.displayScale = Temperature.CELSIUS_SCALE;
			System.out.println(this.displayScale);
		}
		
		return "";
		//TODO remove the return null
		//return null;
	}	
}
