package api.ergast.controller.service.threads;

import java.util.List;

import org.springframework.web.client.RestTemplate;

import api.ergast.model.ErgastResponse;

public class DriverStandingsFetcherThread implements Runnable {
	private RestTemplate restTemplate;
	private String driverStandingUrl;
	private List<ErgastResponse> responseArray;

	public DriverStandingsFetcherThread(RestTemplate restTemplate, String driverStandingUrl,
			List<ErgastResponse> responseArray) {
		super();
		this.restTemplate = restTemplate;
		this.driverStandingUrl = driverStandingUrl;
		this.responseArray = responseArray;
	}

	@Override
	public void run() {
		long time = System.currentTimeMillis();
		responseArray.add(restTemplate.getForObject(driverStandingUrl, ErgastResponse.class));
		System.out.println(System.currentTimeMillis() - time);
	}

}
