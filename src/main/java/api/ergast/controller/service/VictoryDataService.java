package api.ergast.controller.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import api.ergast.model.DriverStanding;
import api.ergast.model.ErgastResponse;
import api.ergast.model.reponse.NationalityWiseStanding;
import api.ergast.utils.ErgastUtils;

@Component
public class VictoryDataService {

	@Value("${ergast.baseUrl}")
	String serviceURL;

	public NationalityWiseStanding[] getVictoryData(RestTemplate restTemplate, int startYear, int endYear) {
		String startYearUrl = ErgastUtils.getDriverStandingUrlByYear(serviceURL, startYear);
		Map<String, NationalityWiseStanding> map = new HashMap<String, NationalityWiseStanding>();
		for (int year = Integer.valueOf(startYear); year <= Integer.valueOf(endYear); year++) {
			ErgastResponse response = restTemplate.getForObject(startYearUrl, ErgastResponse.class);
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
