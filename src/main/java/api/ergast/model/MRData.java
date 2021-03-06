package api.ergast.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MRData {

	private String xmlns;
	private String series;
	private String url;
	private String limit;
	private String offset;
	private String total;

	@JsonProperty("StandingsTable")
	private StandingsTable standingsTable;

	@JsonProperty("RaceTable")
	private RaceTable raceTable;

	@JsonProperty("ConstructorTable")
	private ConstructorTable constructorTable;

	@JsonProperty("DriverTable")
	private DriverTable driverTable;

	public MRData() {

	}

	public String getXmlns() {
		return xmlns;
	}

	public void setXmlns(String xmlns) {
		this.xmlns = xmlns;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getOffset() {
		return offset;
	}

	public void setOffset(String offset) {
		this.offset = offset;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public StandingsTable getStandingsTable() {
		return standingsTable;
	}

	public void setStandingsTable(StandingsTable standingsTable) {
		this.standingsTable = standingsTable;
	}

	public RaceTable getRaceTable() {
		return raceTable;
	}

	public void setRaceTable(RaceTable raceTable) {
		this.raceTable = raceTable;
	}

	public ConstructorTable getConstructorTable() {
		return constructorTable;
	}

	public void setConstructorTable(ConstructorTable constructorTable) {
		this.constructorTable = constructorTable;
	}

	public DriverTable getDriverTable() {
		return driverTable;
	}

	public void setDriverTable(DriverTable driverTable) {
		this.driverTable = driverTable;
	}

}
