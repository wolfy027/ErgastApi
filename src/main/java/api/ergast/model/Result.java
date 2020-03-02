package api.ergast.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Result implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1;
	
    private String number;

    private String positionText;
    
    @JsonProperty("FastestLap")
    private FastestLap fastestLap;
    
    @JsonProperty("Constructor")
    private Constructor constructor;

    private String grid;
    
    @JsonProperty("Driver")
    private Driver driver;

    private String laps;

    private Time Time;

    private String position;

    private String points;

    private String status;

    public String getNumber ()
    {
        return number;
    }

    public void setNumber (String number)
    {
        this.number = number;
    }

    public String getPositionText ()
    {
        return positionText;
    }

    public void setPositionText (String positionText)
    {
        this.positionText = positionText;
    }

    public FastestLap getFastestLap ()
    {
        return fastestLap;
    }

    public void setFastestLap (FastestLap fastestLap)
    {
        this.fastestLap = fastestLap;
    }

    public Constructor getConstructor ()
    {
        return constructor;
    }

    public void setConstructor (Constructor Constructor)
    {
        this.constructor = Constructor;
    }

    public String getGrid ()
    {
        return grid;
    }

    public void setGrid (String grid)
    {
        this.grid = grid;
    }

    public Driver getDriver ()
    {
        return driver;
    }

    public void setDriver (Driver driver)
    {
        this.driver = driver;
    }

    public String getLaps ()
    {
        return laps;
    }

    public void setLaps (String laps)
    {
        this.laps = laps;
    }

    public Time getTime ()
    {
        return Time;
    }

    public void setTime (Time Time)
    {
        this.Time = Time;
    }

    public String getPosition ()
    {
        return position;
    }

    public void setPosition (String position)
    {
        this.position = position;
    }

    public String getPoints ()
    {
        return points;
    }

    public void setPoints (String points)
    {
        this.points = points;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "[number = "+number+", positionText = "+positionText+", FastestLap = "+fastestLap+", Constructor = "+constructor+", grid = "+grid+", Driver = "+driver+", laps = "+laps+", Time = "+Time+", position = "+position+", points = "+points+", status = "+status+"]";
    }
}
