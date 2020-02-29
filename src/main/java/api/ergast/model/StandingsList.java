package api.ergast.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StandingsList {

	private String season;
	private String round;
	@JsonProperty("DriverStandings")
	private DriverStanding[] DriverStandings;

	public StandingsList() {

	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getRound() {
		return round;
	}

	public void setRound(String round) {
		this.round = round;
	}

	public DriverStanding[] getDriverStandings() {
		return DriverStandings;
	}

	public void setDriverStandings(DriverStanding[] driverStandings) {
		DriverStandings = driverStandings;
	}

}
