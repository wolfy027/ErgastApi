package api.ergast.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RaceTable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1;

	@JsonProperty("Races")
	private Race[] races;

	private String round;

	private String season;

	public Race[] getRaces() {
		return races;
	}

	public void setRaces(Race[] Races) {
		this.races = Races;
	}

	public String getRound() {
		return round;
	}

	public void setRound(String round) {
		this.round = round;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	@Override
	public String toString() {
		String races = "";
		for (Race race : this.races) {
			races += race + "\n";
		}
		return "[Races = " + races + ", round = " + round + ", season = " + season + "]";
	}
}
