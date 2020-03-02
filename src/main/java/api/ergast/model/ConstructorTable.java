package api.ergast.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConstructorTable {

	private String season;

	@JsonProperty("Constructors")
	private Constructor[] constructors;

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public Constructor[] getConstructors() {
		return constructors;
	}

	public void setConstructors(Constructor[] constructors) {
		this.constructors = constructors;
	}

}
