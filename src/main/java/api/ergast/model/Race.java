package api.ergast.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Race implements Serializable {

	private static final long serialVersionUID = 1;

	private String date;

	private String round;

	@JsonProperty("Results")
	private Result[] results;

	private String season;

	private String raceName;

	@JsonProperty("Circuit")
	private Circuit circuit;

	private String time;

	@JsonProperty("PitStops")
	private PitStop[] pitStops;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getRound() {
		return round;
	}

	public void setRound(String round) {
		this.round = round;
	}

	public Result[] getResults() {
		return results;
	}

	public void setResults(Result[] results) {
		this.results = results;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getRaceName() {
		return raceName;
	}

	public void setRaceName(String raceName) {
		this.raceName = raceName;
	}

	public Circuit getCircuit() {
		return circuit;
	}

	public void setCircuit(Circuit circuit) {
		this.circuit = circuit;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public PitStop[] getPitStops() {
		return pitStops;
	}

	public void setPitStops(PitStop[] pitStops) {
		this.pitStops = pitStops;
	}

	@Override
	public String toString() {
		return "[date = " + date + ", round = " + round + ", Results = " + results + ", season = " + season
				+ ", raceName = " + raceName + ", Circuit = " + circuit + ", time = " + time + "]";
	}
}