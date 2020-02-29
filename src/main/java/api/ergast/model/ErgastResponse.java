package api.ergast.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErgastResponse {
	public ErgastResponse() {
	}

	@JsonProperty("MRData")
	private MRData MRData;

	public MRData getMRData() {
		return MRData;
	}

	public void setMRData(MRData mRData) {
		MRData = mRData;
	}

}
