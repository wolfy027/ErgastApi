package api.ergast.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DriverTable {

	private String season;
	private String constructorId;

	@JsonProperty("Drivers")
	private Driver[] drivers;

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getConstructorId() {
		return constructorId;
	}

	public void setConstructorId(String constructorId) {
		this.constructorId = constructorId;
	}

	public Driver[] getDrivers() {
		return drivers;
	}

	public void setDrivers(Driver[] drivers) {
		this.drivers = drivers;
	}

}
