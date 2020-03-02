package api.ergast.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FastestLap implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1;
	
	@JsonProperty("AverageSpeed")
    private AverageSpeed averageSpeed;

    private String rank;

    private String lap;
    
    @JsonProperty("Time")
    private Time time;

    public AverageSpeed getAverageSpeed () {
        return averageSpeed;
    }

    public void setAverageSpeed (AverageSpeed averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public String getRank () {
        return rank;
    }

    public void setRank (String rank)
    {
        this.rank = rank;
    }

    public String getLap ()
    {
        return lap;
    }

    public void setLap (String lap)
    {
        this.lap = lap;
    }

    public Time getTime ()
    {
        return time;
    }

    public void setTime (Time time)
    {
        this.time = time;
    }

    @Override
    public String toString()
    {
        return "[AverageSpeed = "+averageSpeed+", rank = "+rank+", lap = "+lap+", Time = "+time+"]";
    }
}