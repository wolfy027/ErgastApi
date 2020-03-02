package api.ergast.controller.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import api.ergast.model.Constructor;
import api.ergast.model.Driver;
import api.ergast.model.ErgastResponse;
import api.ergast.model.PitStop;
import api.ergast.model.Race;
import api.ergast.model.reponse.PitStopStanding;
import api.ergast.utils.ErgastUtils;

@Component
public class PitStopDataService {

	@Value("${ergast.baseUrl}")
	String serviceURL;

	public HashMap<String, String> getConstructorDriverMap(RestTemplate restTemplate, int year) {
		String constructorApiUrl = ErgastUtils.getConstructorsUrlByYear(serviceURL, year);
		ErgastResponse constructorsResponse = restTemplate.getForObject(constructorApiUrl, ErgastResponse.class);

		HashMap<String, String> driverConstructorMap = new HashMap<>();
		for (Constructor constructor : constructorsResponse.getMRData().getConstructorTable().getConstructors()) {
			long time = System.currentTimeMillis();
			String driverApiUrl = ErgastUtils.getDriversUrlByConstructor(serviceURL, year,
					constructor.getConstructorId());
			ErgastResponse driversResponse = restTemplate.getForObject(driverApiUrl, ErgastResponse.class);
			for (Driver driver : driversResponse.getMRData().getDriverTable().getDrivers()) {
				driverConstructorMap.put(driver.getDriverId(), constructor.getConstructorId());
			}
			System.out.println(System.currentTimeMillis() - time);
		}
		return driverConstructorMap;
	}

	public List<PitStopStanding> getPitStopDataByConstructor(RestTemplate restTemplate, int year, int threshold,
			HashMap<String, String> driverConstructorMap) {
		String seasonsApiUrl = ErgastUtils.getSeasonsUrlByYear(serviceURL, year);
		ErgastResponse seasonsResponse = restTemplate.getForObject(seasonsApiUrl, ErgastResponse.class);
		short rounds = Short.valueOf(seasonsResponse.getMRData().getTotal());

		HashMap<String, PitStopStanding> calcMap = new HashMap<>();
		for (int round = 1; round <= rounds; round++) {
			long time = System.currentTimeMillis();
			String pitStopApiUrl = ErgastUtils.getPitStopsUrlByYear(serviceURL, year, round);
			ErgastResponse pitStopResponse = restTemplate.getForObject(pitStopApiUrl, ErgastResponse.class);
			if (pitStopResponse.getMRData().getRaceTable().getRaces().length == 0) {
				continue;
			}
			Race race = pitStopResponse.getMRData().getRaceTable().getRaces()[0];
			PitStop[] pitStops = race.getPitStops();
			for (PitStop pitStop : pitStops) {
				String driverId = pitStop.getDriverId();
				String constructorId = driverConstructorMap.get(driverId);
				if (calcMap.get(constructorId) == null) {
					calcMap.put(constructorId, new PitStopStanding(constructorId));
				}
				calcMap.get(constructorId).getPitStopTimes().add(Double.valueOf(pitStop.getDuration()));
			}
			System.out.println("round->" + round + " -> " + (System.currentTimeMillis() - time));
		}
		List<PitStopStanding> pitStopStandingList = new ArrayList<PitStopStanding>(calcMap.values());
		List<PitStopStanding> pitStopFilteredList = new ArrayList<PitStopStanding>();

		for (int i = 0; i < pitStopStandingList.size(); i++) {
			PitStopStanding current = pitStopStandingList.get(i);
			current.recalculate();
			if (current.getAveragePitStopTime() < threshold) {
				pitStopFilteredList.add(current);
			}
		}
		Collections.sort(pitStopFilteredList);
		Iterator<PitStopStanding> setIterator = pitStopFilteredList.iterator();
		for (int i = 0; setIterator.hasNext();) {
			PitStopStanding current = setIterator.next();
			current.setRank(++i);
		}
		return pitStopFilteredList;
	}

}
