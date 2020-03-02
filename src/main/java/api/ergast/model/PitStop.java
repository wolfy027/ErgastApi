package api.ergast.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PitStop implements Serializable, Comparable<PitStop> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String driverId;
	private String lap;
	private String stop;
	private String time;
	private String duration;

	public String getDriverId() {
		return driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

	public String getLap() {
		return lap;
	}

	public void setLap(String lap) {
		this.lap = lap;
	}

	public String getStop() {
		return stop;
	}

	public void setStop(String stop) {
		this.stop = stop;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	@Override
	public int compareTo(PitStop o) {
		double i = Double.valueOf(o.getDuration()) - Double.valueOf(this.getDuration());
		return (int) i;
	}

	@Override
	public String toString() {
		return "PitStop [driverId=" + driverId + ", lap=" + lap + ", stop=" + stop + ", time=" + time + ", duration="
				+ duration + "]";
	}
}
