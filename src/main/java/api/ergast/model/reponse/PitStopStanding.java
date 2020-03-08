package api.ergast.model.reponse;

import java.util.ArrayList;
import java.util.Collections;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PitStopStanding implements Comparable<PitStopStanding> {

	private int rank;
	private String constructorName;
	private Double averagePitStopTime;
	private Double fastestPitStopTime;
	private Double slowestPitStopTime;

	private ArrayList<Double> pitStopTimes;

	public PitStopStanding(String constructorName) {
		super();
		this.constructorName = constructorName;
		this.pitStopTimes = new ArrayList<>();
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getConstructorName() {
		return constructorName;
	}

	public void setConstructorName(String constructorName) {
		this.constructorName = constructorName;
	}

	public Double getAveragePitStopTime() {
		return averagePitStopTime;
	}

	public void setAveragePitStopTime(Double averagePitStopTime) {
		this.averagePitStopTime = averagePitStopTime;
	}

	public Double getFastestPitStopTime() {
		return fastestPitStopTime;
	}

	public void setFastestPitStopTime(Double fastestPitStopTime) {
		this.fastestPitStopTime = fastestPitStopTime;
	}

	public Double getSlowestPitStopTime() {
		return slowestPitStopTime;
	}

	public void setSlowestPitStopTime(Double slowestPitStopTime) {
		this.slowestPitStopTime = slowestPitStopTime;
	}

	@JsonIgnore
	public ArrayList<Double> getPitStopTimes() {
		return pitStopTimes;
	}

	public void setPitStopTimes(ArrayList<Double> pitStopTimes) {
		this.pitStopTimes = pitStopTimes;
	}

	private double calculateAverage() {
		Double sum = 0.0;
		for (Double pitStopTime : pitStopTimes) {
			sum += pitStopTime;
		}
		return sum / pitStopTimes.size();
	}

	public void recalculate() {
		Collections.sort(pitStopTimes);
		this.fastestPitStopTime = pitStopTimes.get(0);
		this.slowestPitStopTime = pitStopTimes.get(pitStopTimes.size() - 1);
		this.averagePitStopTime = calculateAverage();
	}

	@Override
	public int compareTo(PitStopStanding o) {
		int i = this.getAveragePitStopTime().compareTo(o.getAveragePitStopTime());
		if (i != 0)
			return i;
		return o.getFastestPitStopTime().compareTo(this.getFastestPitStopTime());
	}

	@Override
	public String toString() {
		return rank + (",") + constructorName + (",") + averagePitStopTime + (",") + fastestPitStopTime + (",")
				+ slowestPitStopTime;
	}

}
