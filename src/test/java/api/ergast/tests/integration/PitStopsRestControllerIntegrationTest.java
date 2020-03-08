package api.ergast.tests.integration;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import api.ergast.application.ErgastApiApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ErgastApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class PitStopsRestControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@Value("${pitstop.data.csv.header}")
	String pitStopsDataCsvHeader;

	@Value("${pitstop.data.year}")
	String yearParam;

	@Value("${pitstop.data.threshold}")
	String thresholdParam;

	@Value("${pitstop.data.invalidYear}")
	String invalidYear;

	@Test
	public void givenInvalidYear_whenGetPitStops_thenStatus400() throws Exception {
		mvc.perform(post("/pitstops").contentType(MediaType.APPLICATION_JSON).queryParam("type", "json").content(
				"{\n" + "    \"year\":" + invalidYear + ",\n" + "    \"threshold\":" + thresholdParam + "\n" + "}"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void givenTypeParamAsCsv_whenGetPitStops_thenResponseCsv() throws Exception {
		mvc.perform(post("/pitstops").contentType(MediaType.APPLICATION_JSON).queryParam("type", "csv").content(
				"{\n" + "    \"year\":" + yearParam + ",\n" + "    \"threshold\":" + thresholdParam + "\n" + "}"))
				.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith("text/csv"));
	}

	@Test
	public void givenTypeParamAsCsv_whenGetPitStops_thenValidCsvHeaders() throws Exception {
		MvcResult result = mvc
				.perform(post("/pitstops").contentType(MediaType.APPLICATION_JSON).queryParam("type", "csv")
						.content("{\n" + "    \"year\":" + yearParam + ",\n" + "    \"threshold\":" + thresholdParam
								+ "\n" + "}"))
				.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith("text/csv")).andReturn();
		String csvResponseHeader = result.getResponse().getContentAsString().split("\n")[0].trim();
		String csvHeaderFromProperty = pitStopsDataCsvHeader.trim();
		Assert.assertTrue(csvResponseHeader.equals(csvHeaderFromProperty));
	}

	@Test
	public void givenTypeParamAsJson_whenGetPitStops_thenResponseJson() throws Exception {
		mvc.perform(post("/pitstops").contentType(MediaType.APPLICATION_JSON).queryParam("type", "json").content(
				"{\n" + "    \"year\":" + yearParam + ",\n" + "    \"threshold\":" + thresholdParam + "\n" + "}"))
				.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

	@Test
	public void givenTypeParamAsJson_whenGetPitStops_thenValidResponseJson() throws Exception {
		mvc.perform(post("/pitstops").contentType(MediaType.APPLICATION_JSON).queryParam("type", "json").content(
				"{\n" + "    \"year\":" + yearParam + ",\n" + "    \"threshold\":" + thresholdParam + "\n" + "}"))
				.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].constructorName", isA(String.class)))
				.andExpect(jsonPath("$[0].rank", isA(Integer.class))).andExpect(jsonPath("$[0].rank", is(1)))
				.andExpect(jsonPath("$[0].averagePitStopTime", isA(Double.class)))
				.andExpect(jsonPath("$[0].fastestPitStopTime", isA(Double.class)))
				.andExpect(jsonPath("$[0].slowestPitStopTime", isA(Double.class)));
	}

}
