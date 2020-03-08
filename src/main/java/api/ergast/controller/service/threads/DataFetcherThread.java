package api.ergast.controller.service.threads;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import api.ergast.model.ErgastResponse;

public class DataFetcherThread implements Runnable {
	private RestTemplate restTemplate;
	private String dataApiUrl;
	private List<ErgastResponse> responseArray;

	private static final Logger LOGGER = LoggerFactory.getLogger(DataFetcherThread.class);

	public DataFetcherThread(RestTemplate restTemplate, String driverStandingUrl, List<ErgastResponse> responseArray) {
		super();
		this.restTemplate = restTemplate;
		this.dataApiUrl = driverStandingUrl;
		this.responseArray = responseArray;
	}

	@Override
	public void run() {
		long time = System.currentTimeMillis();
		responseArray.add(restTemplate.getForObject(dataApiUrl, ErgastResponse.class));
		LOGGER.info("Time taken to fetch data : " + (System.currentTimeMillis() - time) + "\t|URL|\t" + dataApiUrl);
	}

}
