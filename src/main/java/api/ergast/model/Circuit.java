package api.ergast.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Circuit implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1;
	
	private String circuitId;

    private String circuitName;
    
    @JsonProperty("Location")
    private Location location;

    public String getCircuitId ()
    {
        return circuitId;
    }

    public void setCircuitId (String circuitId)
    {
        this.circuitId = circuitId;
    }

    public String getCircuitName ()
    {
        return circuitName;
    }

    public void setCircuitName (String circuitName)
    {
        this.circuitName = circuitName;
    }

    public Location getLocation ()
    {
        return location;
    }

    public void setLocation (Location location)
    {
        this.location = location;
    }

    @Override
    public String toString()
    {
        return "[circuitId = "+circuitId+", circuitName = "+circuitName+", Location = "+location+"]";
    }
}