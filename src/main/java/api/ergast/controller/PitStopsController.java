package api.ergast.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import api.ergast.model.Constructor;
import api.ergast.model.Driver;
import api.ergast.model.ErgastResponse;
import api.ergast.model.PitStop;
import api.ergast.model.Race;
import api.ergast.model.reponse.PitStopStanding;
import api.ergast.model.request.PitStopRequest;
import api.ergast.utils.ErgastUtils;

@RestController
@RequestMapping("/")
public class PitStopsController {

	@Autowired
	RestTemplate restTemplate;

	@Value("${ergast.baseUrl}")
	String serviceURL;

	@RequestMapping(value = "pitstops", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object pitstops(@RequestBody(required = true) PitStopRequest request,
			@RequestParam("type") String outputType) {
		int year = request.getYear();
		if (year < 1950 || year > Calendar.getInstance().get(Calendar.YEAR)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		int threshold = request.getThreshold();
		String constructorApiUrl = ErgastUtils.getConstructorsUrlByYear(serviceURL, year);
		ErgastResponse constructorsResponse = restTemplate.getForObject(constructorApiUrl, ErgastResponse.class);

		HashMap<String, String> driverConstructorMap = new HashMap<>();
		for (Constructor constructor : constructorsResponse.getMRData().getConstructorTable().getConstructors()) {
			String driverApiUrl = ErgastUtils.getDriversUrlByConstructor(serviceURL, year,
					constructor.getConstructorId());
			ErgastResponse driversResponse = restTemplate.getForObject(driverApiUrl, ErgastResponse.class);
			for (Driver driver : driversResponse.getMRData().getDriverTable().getDrivers()) {
				driverConstructorMap.put(driver.getDriverId(), constructor.getConstructorId());
			}
		}

		String seasonsApiUrl = ErgastUtils.getSeasonsUrlByYear(serviceURL, year);
		ErgastResponse seasonsResponse = restTemplate.getForObject(seasonsApiUrl, ErgastResponse.class);
		int rounds = Integer.valueOf(seasonsResponse.getMRData().getTotal());

		HashMap<String, PitStopStanding> calcMap = new HashMap<>();
		for (int round = 1; round <= rounds; round++) {
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

		if (pitStopFilteredList.size() == 0) {
			return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
		}
		Collections.sort(pitStopFilteredList);
		Iterator<PitStopStanding> setIterator = pitStopFilteredList.iterator();
		for (int i = 0; setIterator.hasNext();) {
			PitStopStanding current = setIterator.next();
			current.setRank(++i);
		}

		return outputType.equalsIgnoreCase("csv") ? ErgastUtils.getCsvOutput(pitStopFilteredList)
				: new ResponseEntity<>(pitStopFilteredList, HttpStatus.OK);

	}

}
