package api.ergast.tests.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import api.ergast.controller.service.VictoryDataService;
import api.ergast.model.reponse.NationalityWiseStanding;

@RunWith(SpringRunner.class)
@WebMvcTest(ErgastServiceController.class)
@ContextConfiguration(classes = ErgastApiApplication.class)
public class VictoryDataControllerTest {

	@MockBean
	private RestTemplate restTemplate;

	@MockBean
	private VictoryDataService victoryDataService;

	@Autowired
	private MockMvc mvc;

	@Test
	public void givenMockingIsDoneByMockito_whenGetIsCalled_shouldReturnMockedCsvObject() throws Exception {
		NationalityWiseStanding standing1 = new NationalityWiseStanding("British", 25);
		standing1.setRank(2);
		NationalityWiseStanding standing2 = new NationalityWiseStanding("Polish", 50);
		standing2.setRank(1);
		NationalityWiseStanding[] standings = new NationalityWiseStanding[] { standing2, standing1 };
		Mockito.when(victoryDataService.getVictoryData(restTemplate, 2010, 2011)).thenReturn(standings);

		mvc.perform(get("/victories").queryParam("start", "2010").queryParam("end", "2011").queryParam("type", "csv"))
				.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith("text/csv"));
	}

	@Test
	public void givenMockingIsDoneByMockito_whenGetIsCalled_shouldReturnMockedJsonObject() throws Exception {
		NationalityWiseStanding standing1 = new NationalityWiseStanding("British", 25);
		standing1.setRank(2);
		NationalityWiseStanding standing2 = new NationalityWiseStanding("Polish", 50);
		standing2.setRank(1);
		NationalityWiseStanding[] standings = new NationalityWiseStanding[] { standing2, standing1 };
		Mockito.when(victoryDataService.getVictoryData(restTemplate, 2010, 2011)).thenReturn(standings);

		mvc.perform(get("/victories").queryParam("start", "2010").queryParam("end", "2011").queryParam("type", "json"))
				.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

}
