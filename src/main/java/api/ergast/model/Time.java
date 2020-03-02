package api.ergast.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Time implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1;
	
	private String time;

    private String millis;

    public String getTime () {
        return time;
    }

    public void setTime (String time) {
        this.time = time;
    }

    public String getMillis () {
        return millis;
    }

    public void setMillis (String millis) {
        this.millis = millis;
    }

    @Override
    public String toString() {
        return "[time = "+time+", millis = "+millis+"]";
    }
}
