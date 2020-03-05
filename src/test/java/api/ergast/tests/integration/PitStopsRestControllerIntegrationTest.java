package api.ergast.tests.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import api.ergast.application.ErgastApiApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ErgastApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
// @TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class PitStopsRestControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void givenInvalidYear_whenGetPitStops_thenStatus400() throws Exception {
		mvc.perform(post("/pitstops").contentType(MediaType.APPLICATION_JSON).queryParam("type", "json")
				.content("{\n" + "    \"year\":1949,\n" + "    \"threshold\":30\n" + "}"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void givenTypeParamAsJson_whenGetPitStops_thenResponseJson() throws Exception {
		mvc.perform(post("/pitstops").contentType(MediaType.APPLICATION_JSON).queryParam("type", "json")
				.content("{\n" + "    \"year\":2018,\n" + "    \"threshold\":30\n" + "}")).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

	@Test
	public void givenTypeParamAsCsv_whenGetPitStops_thenResponseCsv() throws Exception {
		mvc.perform(post("/pitstops").contentType(MediaType.APPLICATION_JSON).queryParam("type", "csv")
				.content("{\n" + "    \"year\":2018,\n" + "    \"threshold\":30\n" + "}")).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith("text/csv"));
	}

}
