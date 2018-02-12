package model;

import java.text.DecimalFormat;

public class Temperature {	
	public static final int CELSIUS_SCALE = 0;
	public static final int FAHRENHEIT_SCALE = 1;
	
	private double temperature;	
	private int scale;

	public Temperature(int temperature, int scale) {
		this.temperature = temperature;
		this.scale = scale;
	}

	private double getTemperature(int scale) {
		if (this.scale == scale) { // No scale conversion is needed
			return temperature;
		} else {
			if (scale == FAHRENHEIT_SCALE) { // Convert to FAHRENHEIT_SCALE
				return temperature * 1.8 + 32;
			} else { // Convert to CELSIUS_SCALE
				return (temperature - 32) / 1.8;
			}
		}
	}

	public String getTemperatureAsString(int scale) {
		DecimalFormat df = new DecimalFormat("#.## ");
		return df.format(getTemperature(scale)) 
				+ (scale == CELSIUS_SCALE ? "ºC" : "ºF");
	}
	
	@Override
	public String toString() {
		return this.getTemperatureAsString(scale);
	}
	
}
