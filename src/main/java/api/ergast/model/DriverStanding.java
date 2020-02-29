package api.ergast.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DriverStanding {

	private String position;
	private String positionText;
	private String points;
	private String wins;
	@JsonProperty("Driver")
	private Driver Driver;
	@JsonProperty("Constructors")
	private Constructor[] Constructors;

	public DriverStanding() {

	}

	public Driver getDriver() {
		return Driver;
	}

	public void setDriver(Driver driver) {
		Driver = driver;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPositionText() {
		return positionText;
	}

	public void setPositionText(String positionText) {
		this.positionText = positionText;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public String getWins() {
		return wins;
	}

	public void setWins(String wins) {
		this.wins = wins;
	}

	public Constructor[] getConstructors() {
		return Constructors;
	}

	public void setConstructors(Constructor[] constructors) {
		Constructors = constructors;
	}

}
