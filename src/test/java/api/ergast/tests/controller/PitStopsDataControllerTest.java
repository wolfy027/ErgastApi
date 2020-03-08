package api.ergast.tests.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import api.ergast.application.ErgastApiApplication;
import api.ergast.controller.ErgastServiceController;
import api.ergast.controller.service.PitStopDataService;
import api.ergast.model.reponse.PitStopStanding;

@RunWith(SpringRunner.class)
@WebMvcTest(ErgastServiceController.class)
@ContextConfiguration(classes = ErgastApiApplication.class)
public class PitStopsDataControllerTest {

	@MockBean
	private RestTemplate restTemplate;

	@MockBean
	private PitStopDataService pitStopDataService;

	@Autowired
	private MockMvc mvc;

	@Test
	public void givenMockingIsDoneByMockito_whenGetIsCalled_shouldReturnMockedCsvObject() throws Exception {
		PitStopStanding pitStopStanding1 = new PitStopStanding("mclaren");
		pitStopStanding1.setRank(1);
		pitStopStanding1.setAveragePitStopTime(18.5);
		pitStopStanding1.setFastestPitStopTime(10.5);
		pitStopStanding1.setSlowestPitStopTime(30.56);
		PitStopStanding pitStopStanding2 = new PitStopStanding("redbull");
		pitStopStanding2.setRank(2);
		pitStopStanding2.setAveragePitStopTime(20.5);
		pitStopStanding2.setFastestPitStopTime(12.5);
		pitStopStanding2.setSlowestPitStopTime(32.56);
		List<PitStopStanding> pitStopFilteredList = new ArrayList<PitStopStanding>();
		pitStopFilteredList.add(pitStopStanding1);
		pitStopFilteredList.add(pitStopStanding2);
		Mockito.when(pitStopDataService.getPitStopDataByConstructor(restTemplate, 2010, 25,
				new HashMap<String, List<String>>())).thenReturn(pitStopFilteredList);

		mvc.perform(post("/pitstops").contentType(MediaType.APPLICATION_JSON).queryParam("type", "csv")
				.content("{\n" + "    \"year\":2010,\n" + "    \"threshold\":25\n" + "}")).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith("text/csv"));
	}

	@Test
	public void givenMockingIsDoneByMockito_whenGetIsCalled_shouldReturnMockedJsonObject() throws Exception {
		PitStopStanding pitStopStanding1 = new PitStopStanding("mclaren");
		pitStopStanding1.setRank(1);
		pitStopStanding1.setAveragePitStopTime(18.5);
		pitStopStanding1.setFastestPitStopTime(10.5);
		pitStopStanding1.setSlowestPitStopTime(30.56);
		PitStopStanding pitStopStanding2 = new PitStopStanding("redbull");
		pitStopStanding2.setRank(2);
		pitStopStanding2.setAveragePitStopTime(20.5);
		pitStopStanding2.setFastestPitStopTime(12.5);
		pitStopStanding2.setSlowestPitStopTime(32.56);
		List<PitStopStanding> pitStopFilteredList = new ArrayList<PitStopStanding>();
		pitStopFilteredList.add(pitStopStanding1);
		pitStopFilteredList.add(pitStopStanding2);
		Mockito.when(pitStopDataService.getPitStopDataByConstructor(restTemplate, 2010, 25,
				new HashMap<String, List<String>>())).thenReturn(pitStopFilteredList);

		mvc.perform(post("/pitstops").contentType(MediaType.APPLICATION_JSON).queryParam("type", "json")
				.content("{\n" + "    \"year\":2010,\n" + "    \"threshold\":25\n" + "}")).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}
}
