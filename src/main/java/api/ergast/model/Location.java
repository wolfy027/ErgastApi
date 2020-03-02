package api.ergast.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Location  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1;
	private String country;

    private String locality;

    private String lat;

    private String _long;

    public String getCountry ()
    {
        return country;
    }

    public void setCountry (String country)
    {
        this.country = country;
    }

    public String getLocality ()
    {
        return locality;
    }

    public void setLocality (String locality)
    {
        this.locality = locality;
    }

    public String getLat ()
    {
        return lat;
    }

    public void setLat (String lat)
    {
        this.lat = lat;
    }

    public String getLong ()
    {
        return _long;
    }

    public void setLong (String _long)
    {
        this._long = _long;
    }

    @Override
    public String toString()
    {
        return "[country = "+country+", locality = "+locality+", lat = "+lat+", long = "+_long+"]";
    }
}