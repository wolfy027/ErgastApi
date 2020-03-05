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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import api.ergast.controller.service.threads.DriverStandingsFetcherThread;
import api.ergast.model.DriverStanding;
import api.ergast.model.ErgastResponse;
import api.ergast.model.reponse.NationalityWiseStanding;
import api.ergast.utils.ErgastUtils;

@Component
public class VictoryDataService {

	@Value("${ergast.baseUrl}")
	String serviceURL;

	@SuppressWarnings({ "unchecked" })
	public NationalityWiseStanding[] getVictoryData(RestTemplate restTemplate, int startYear, int endYear) {
		int yearDiff = endYear - startYear;
		Map<String, NationalityWiseStanding> map = new HashMap<String, NationalityWiseStanding>();
		List<ErgastResponse> responseArray = new Vector<ErgastResponse>();
		int poolSize = yearDiff + 1;
		ExecutorService service = Executors.newFixedThreadPool(poolSize);
		List<Future<Runnable>> futures = new ArrayList<Future<Runnable>>();
		for (int yrCtr = 0; yrCtr <= yearDiff; yrCtr++) {
			String driverStandingUrl = ErgastUtils.getDriverStandingUrlByYear(serviceURL, (startYear + yrCtr));
			Runnable r = new DriverStandingsFetcherThread(restTemplate, driverStandingUrl, responseArray);
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
		for (ErgastResponse response : responseArray) {
			for (DriverStanding driverStanding : response.getMRData().getStandingsTable().getStandingsLists()[0]
					.getDriverStandings()) {

				if (map.containsKey(driverStanding.getDriver().getNationality())) {
					map.get(driverStanding.getDriver().getNationality())
							.addToVictoryTally(Integer.valueOf(driverStanding.getWins()));

				} else {
					map.put(driverStanding.getDriver().getNationality(), new NationalityWiseStanding(
							driverStanding.getDriver().getNationality(), Integer.valueOf(driverStanding.getWins())));
				}
			}

		}
		List<NationalityWiseStanding> list = new ArrayList<NationalityWiseStanding>(map.values());
		Collections.sort(list);
		NationalityWiseStanding[] array = new NationalityWiseStanding[10];
		for (int rank = 0; rank < 10;) {
			array[rank] = list.get(rank);
			array[rank].setRank(++rank);
		}
		return array;
	}

}
