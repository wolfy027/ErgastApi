package api.ergast.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@Component("standingController")
public class StandingsController {
//
//	@Autowired
//	RestTemplate restTemplate;
//
//	@Value("${ergast.baseUrl}")
//	String serviceURL;
//
//	@RequestMapping(value = "victories", method = RequestMethod.GET)
//	public Object top10Victories(@RequestParam("start") int startYear, @RequestParam("end") int endYear,
//			@RequestParam("type") String outputType) {
//		if (startYear > endYear) {
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//		String startYearUrl = ErgastUtils.getDriverStandingUrlByYear(serviceURL, startYear);
//
//		Map<String, NationalityWiseStanding> map = new HashMap<String, NationalityWiseStanding>();
//		for (int year = Integer.valueOf(startYear); year <= Integer.valueOf(endYear); year++) {
//			ErgastResponse response = restTemplate.getForObject(startYearUrl, ErgastResponse.class);
//			for (DriverStanding driverStanding : response.getMRData().getStandingsTable().getStandingsLists()[0]
//					.getDriverStandings()) {
//
//				if (map.containsKey(driverStanding.getDriver().getNationality())) {
//					map.get(driverStanding.getDriver().getNationality())
//							.addToVictoryTally(Integer.valueOf(driverStanding.getWins()));
//
//				} else {
//					map.put(driverStanding.getDriver().getNationality(), new NationalityWiseStanding(
//							driverStanding.getDriver().getNationality(), Integer.valueOf(driverStanding.getWins())));
//				}
//
//			}
//		}
//		
//		List<NationalityWiseStanding> list= new ArrayList<NationalityWiseStanding>(map.values());
//		Collections.sort(list);
//		NationalityWiseStanding[] array=new NationalityWiseStanding[10];
//		for(int rank = 0;rank<10;) {
//			array[rank]=list.get(rank);
//			array[rank].setRank(++rank);
//		}
//
//		return outputType.equalsIgnoreCase("csv") ? ErgastUtils.getCsvOutput(Arrays.asList(array)) : array;
//	}
//
}
