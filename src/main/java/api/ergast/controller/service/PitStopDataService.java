package api.ergast.controller.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import api.ergast.controller.service.threads.DataFetcherThread;
import api.ergast.model.Constructor;
import api.ergast.model.Driver;
import api.ergast.model.ErgastResponse;
import api.ergast.model.PitStop;
import api.ergast.model.Race;
import api.ergast.model.reponse.PitStopStanding;
import api.ergast.utils.ErgastUtils;

@Component
public class PitStopDataService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PitStopDataService.class);

	@Value("${ergast.baseUrl}")
	String serviceURL;

	@SuppressWarnings("unchecked")
	public HashMap<String, List<String>> getConstructorDriverMap(RestTemplate restTemplate, int year) {
		String constructorApiUrl = ErgastUtils.getConstructorsUrlByYear(serviceURL, year);
		ErgastResponse constructorsResponse = restTemplate.getForObject(constructorApiUrl, ErgastResponse.class);

		HashMap<String, List<String>> driverConstructorMap = new HashMap<>();
		List<ErgastResponse> responseArray = new Vector<ErgastResponse>();
		ExecutorService service = Executors.newCachedThreadPool();
		List<Future<Runnable>> futures = new ArrayList<Future<Runnable>>();
		for (Constructor constructor : constructorsResponse.getMRData().getConstructorTable().getConstructors()) {
			String driverApiUrl = ErgastUtils.getDriversUrlByConstructor(serviceURL, year,
					constructor.getConstructorId());
			Runnable r = new DataFetcherThread(restTemplate, driverApiUrl, responseArray);
			Future<Runnable> f = (Future<Runnable>) service.submit(r);
			futures.add(f);
		}
		for (Future<Runnable> f : futures) {
			try {
				f.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		service.shutdownNow();
		for (ErgastResponse driversResponse : responseArray) {
			for (Driver driver : driversResponse.getMRData().getDriverTable().getDrivers()) {
				List<String> constructorList = driverConstructorMap.get(driver.getDriverId());
				if (constructorList == null) {
					constructorList = new Vector<String>();
				}
				constructorList.add(driversResponse.getMRData().getDriverTable().getConstructorId());
				driverConstructorMap.put(driver.getDriverId(), constructorList);
				LOGGER.info("Driver ID : " + driver.getDriverId() + " | Constructor(s) : " + constructorList);
			}
		}
		return driverConstructorMap;
	}

	@SuppressWarnings("unchecked")
	public List<PitStopStanding> getPitStopDataByConstructor(RestTemplate restTemplate, int year, int threshold,
			HashMap<String, List<String>> driverConstructorMap) {
		String seasonsApiUrl = ErgastUtils.getSeasonsUrlByYear(serviceURL, year);
		ErgastResponse seasonsResponse = restTemplate.getForObject(seasonsApiUrl, ErgastResponse.class);
		short rounds = Short.valueOf(seasonsResponse.getMRData().getTotal());

		HashMap<String, PitStopStanding> calcMap = new HashMap<>();
		List<ErgastResponse> responseArray = new Vector<ErgastResponse>();
		ExecutorService service = Executors.newWorkStealingPool();
		List<Future<Runnable>> futures = new ArrayList<Future<Runnable>>();
		LOGGER.info(year + " -> Round Count : " + rounds);
		for (int round = 1; round <= rounds; round++) {
			String pitStopApiUrl = ErgastUtils.getPitStopsUrlByYear(serviceURL, year, round);
			Runnable r = new DataFetcherThread(restTemplate, pitStopApiUrl, responseArray);
			Future<Runnable> f = (Future<Runnable>) service.submit(r);
			futures.add(f);

		}
		for (Future<Runnable> f : futures) {
			try {
				f.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		service.shutdownNow();
		for (ErgastResponse pitStopResponse : responseArray) {
			if (pitStopResponse.getMRData().getRaceTable().getRaces().length == 0) {
				continue;
			}
			Race race = pitStopResponse.getMRData().getRaceTable().getRaces()[0];
			PitStop[] pitStops = race.getPitStops();
			for (PitStop pitStop : pitStops) {
				String driverId = pitStop.getDriverId();
				for (String constructorId : driverConstructorMap.get(driverId)) {
					if (calcMap.get(constructorId) == null) {
						calcMap.put(constructorId, new PitStopStanding(constructorId));
						LOGGER.info("Entry Created for Constructor : " + constructorId);
					}
					String pitStopDuration = pitStop.getDuration();
					Double pitStopDurationInSeconds = 0.0;
					if (pitStopDuration.contains(":")) {
						String[] units = pitStopDuration.split(":"); // will break the string up into an array
						int minutes = Integer.parseInt(units[0]); // first element
						double seconds = Double.parseDouble(units[1]); // second element
						pitStopDurationInSeconds = 60 * minutes + seconds;
					} else {
						pitStopDurationInSeconds = Double.valueOf(pitStop.getDuration());
					}
					calcMap.get(constructorId).getPitStopTimes().add(pitStopDurationInSeconds);
				}
			}
		}
		List<PitStopStanding> pitStopStandingList = new ArrayList<PitStopStanding>(calcMap.values());
		List<PitStopStanding> pitStopFilteredList = new ArrayList<PitStopStanding>();

		for (int i = 0; i < pitStopStandingList.size(); i++) {
			PitStopStanding current = pitStopStandingList.get(i);
			current.recalculate();
			if (current.getAveragePitStopTime() <= threshold) {
				pitStopFilteredList.add(current);
			}
		}
		Collections.sort(pitStopFilteredList);
		Iterator<PitStopStanding> setIterator = pitStopFilteredList.iterator();
		for (int i = 0; setIterator.hasNext();) {
			PitStopStanding current = setIterator.next();
			if (i > 9) {
				pitStopFilteredList.remove(current);
			}
			current.setRank(++i);
		}
		return pitStopFilteredList;
	}

}
