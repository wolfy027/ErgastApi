package api.ergast.controller.service.threads;

import org.springframework.web.client.RestTemplate;

import api.ergast.controller.service.PitStopDataService;
import api.ergast.model.Constructor;
import api.ergast.model.Driver;
import api.ergast.model.ErgastResponse;
import api.ergast.utils.ErgastUtils;

public class PopulateConstructorDataThread implements Runnable {

	private Constructor constructor;
	private RestTemplate restTemplate;;
	private String serviceURL;
	private int year;
	private PitStopDataService pitStopDataService;

	public PopulateConstructorDataThread(PitStopDataService pitStopDataService, Constructor constructor,
			RestTemplate restTemplate, String serviceURL, int year) {
		super();
		this.pitStopDataService = pitStopDataService;
		this.constructor = constructor;
		this.restTemplate = restTemplate;
		this.serviceURL = serviceURL;
		this.year = year;
	}

	@Override
	public void run() {
//		long time = System.currentTimeMillis();
//		String driverApiUrl = ErgastUtils.getDriversUrlByConstructor(serviceURL, year, constructor.getConstructorId());
//		ErgastResponse driversResponse = restTemplate.getForObject(driverApiUrl, ErgastResponse.class);
//		for (Driver driver : driversResponse.getMRData().getDriverTable().getDrivers()) {
//			pitStopDataService.getMap().put(driver.getDriverId(), constructor.getConstructorId());
//		}
//		System.out.println(System.currentTimeMillis() - time);
//
	}

}
