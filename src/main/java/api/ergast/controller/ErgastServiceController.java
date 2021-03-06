package api.ergast.controller;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import api.ergast.controller.service.PitStopDataService;
import api.ergast.controller.service.VictoryDataService;
import api.ergast.model.reponse.NationalStanding;
import api.ergast.model.reponse.PitStopStanding;
import api.ergast.model.request.PitStopRequest;
import api.ergast.utils.Constants;
import api.ergast.utils.CsvUtils;

@RestController
@RequestMapping("/")
@Component("ergastServiceController")
public class ErgastServiceController {

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	private VictoryDataService victoryDataService;

	@Autowired
	private PitStopDataService pitStopDataService;

	@Value("${ergast.baseUrl}")
	String serviceURL;

	@RequestMapping(value = "victories", method = RequestMethod.GET)
	public ResponseEntity<NationalStanding[]> top10Victories(@RequestParam("start") int startYear,
			@RequestParam("end") int endYear, @RequestParam("type") String outputType, HttpServletResponse response) {
		if (startYear > endYear) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		NationalStanding[] resultArray = victoryDataService.getVictoryData(restTemplate, startYear, endYear);

		if (outputType.equalsIgnoreCase("csv")) {
			CsvUtils.writeCsvResponse(Constants.VICTORY_DATA_HEADERS, "victoriesByNationality.csv", response,
					Arrays.asList(resultArray));
		} else if (outputType.equalsIgnoreCase("json")) {
			return new ResponseEntity<>(resultArray, HttpStatus.OK);
		}
		return null;
	}

	@RequestMapping(value = "pitstops", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<PitStopStanding>> pitstops(@RequestBody(required = true) PitStopRequest request,
			@RequestParam("type") String outputType, HttpServletResponse response) {
		int year = request.getYear();
		if (year < 1950 || year > Calendar.getInstance().get(Calendar.YEAR)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		int threshold = request.getThreshold();
		HashMap<String, List<String>> driverConstructorMap = pitStopDataService.getConstructorDriverMap(restTemplate,
				year);
		System.out.println(driverConstructorMap);
		List<PitStopStanding> pitStopFilteredList = pitStopDataService.getPitStopDataByConstructor(restTemplate, year,
				threshold, driverConstructorMap);
		if (pitStopFilteredList.size() == 0) {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
		if (outputType.equalsIgnoreCase("csv")) {
			CsvUtils.writeCsvResponse(Constants.PITSTOPS_DATA_HEADERS, "pitStopDurationByConstructor.csv", response,
					pitStopFilteredList);
		} else if (outputType.equalsIgnoreCase("json")) {
			return new ResponseEntity<>(pitStopFilteredList, HttpStatus.OK);
		}
		return null;
	}

}
