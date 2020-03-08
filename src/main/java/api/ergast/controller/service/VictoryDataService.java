package api.ergast.controller.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import api.ergast.model.DriverStanding;
import api.ergast.model.ErgastResponse;
import api.ergast.model.reponse.NationalStanding;
import api.ergast.utils.ErgastUtils;

@Component
public class VictoryDataService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PitStopDataService.class);

	@Value("${ergast.baseUrl}")
	String serviceURL;

	@SuppressWarnings({ "unchecked" })
	public NationalStanding[] getVictoryData(RestTemplate restTemplate, int startYear, int endYear) {
		LOGGER.info("START YEAR : " + startYear);
		LOGGER.info("END YEAR : " + endYear);
		int yearDiff = endYear - startYear;
		Map<String, NationalStanding> map = new HashMap<String, NationalStanding>();
		List<ErgastResponse> responseArray = new Vector<ErgastResponse>();
		int poolSize = yearDiff + 1;
		ExecutorService service = Executors.newFixedThreadPool(poolSize);
		// Fetch Driver Standings for each year in parallel
		List<Future<Runnable>> futures = new ArrayList<Future<Runnable>>();
		for (int yrCtr = 0; yrCtr <= yearDiff; yrCtr++) {
			String driverStandingUrl = ErgastUtils.getDriverStandingUrlByYear(serviceURL, (startYear + yrCtr));
			Runnable r = new DataFetcherThread(restTemplate, driverStandingUrl, responseArray);
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
		// Sum up victory count using each Driver Standing
		for (ErgastResponse response : responseArray) {
			for (DriverStanding driverStanding : response.getMRData().getStandingsTable().getStandingsLists()[0]
					.getDriverStandings()) {

				if (map.containsKey(driverStanding.getDriver().getNationality())) {
					map.get(driverStanding.getDriver().getNationality())
							.addToVictoryTally(Integer.valueOf(driverStanding.getWins()));

				} else {
					map.put(driverStanding.getDriver().getNationality(), new NationalStanding(
							driverStanding.getDriver().getNationality(), Integer.valueOf(driverStanding.getWins())));
					LOGGER.info("Entry Created for Nationality : " + driverStanding.getDriver().getNationality());
				}
			}
		}
		List<NationalStanding> list = new ArrayList<NationalStanding>(map.values());
		Collections.sort(list);
		NationalStanding[] array = new NationalStanding[10];
		// Set Rank for each National Standing
		for (int rank = 0; rank < 10;) {
			array[rank] = list.get(rank);
			array[rank].setRank(++rank);
		}
		return array;
	}

}
