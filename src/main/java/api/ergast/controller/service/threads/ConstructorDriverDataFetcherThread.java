package api.ergast.controller.service.threads;

import java.util.List;

import org.springframework.web.client.RestTemplate;

import api.ergast.model.ErgastResponse;

public class ConstructorDriverDataFetcherThread implements Runnable {
	private RestTemplate restTemplate;
	private String driverApiUrl;
	private List<ErgastResponse> responseArray;

	public ConstructorDriverDataFetcherThread(RestTemplate restTemplate, String driverApiUrl,
			List<ErgastResponse> responseArray) {
		super();
		this.restTemplate = restTemplate;
		this.driverApiUrl = driverApiUrl;
		this.responseArray = responseArray;
	}

	@Override
	public void run() {
		long time = System.currentTimeMillis();
		responseArray.add(restTemplate.getForObject(driverApiUrl, ErgastResponse.class));
		System.out.println(System.currentTimeMillis() - time);
	}
}
