package api.ergast.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StandingsTable {
	private String season;
	@JsonProperty("StandingsLists")
	private StandingsList[] StandingsLists;

	public StandingsTable() {

	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public StandingsList[] getStandingsLists() {
		return StandingsLists;
	}

	public void setStandingsLists(StandingsList[] standingsLists) {
		StandingsLists = standingsLists;
	}
}
