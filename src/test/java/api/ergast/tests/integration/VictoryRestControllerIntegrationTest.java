package api.ergast.tests.integration;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
public class VictoryRestControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@Value("${victory.data.csv.header}")
	String victoryDataCsvHeader;

	@Value("${victory.data.startYear}")
	String startYear;

	@Value("${victory.data.endYear}")
	String endYear;

	@Value("${victory.data.invalidEndYear}")
	String invalidEndYear;

	@Test
	public void givenInvalidYears_whenGetTop10Victories_thenStatus400() throws Exception {
		mvc.perform(get("/victories").contentType(MediaType.APPLICATION_JSON).queryParam("start", startYear)
				.queryParam("end", invalidEndYear).queryParam("type", "json")).andExpect(status().isBadRequest());
	}

	@Test
	public void givenTypeParamAsCsv_whenGetTop10Victories_thenResponseCsv() throws Exception {
		mvc.perform(
				get("/victories").queryParam("start", startYear).queryParam("end", endYear).queryParam("type", "csv"))
				.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith("text/csv"));
	}

	@Test
	public void givenTypeParamAsCsv_whenGetTop10Victories_thenValidCsvHeaders() throws Exception {
		MvcResult result = mvc.perform(
				get("/victories").queryParam("start", startYear).queryParam("end", endYear).queryParam("type", "csv"))
				.andExpect(status().isOk()).andReturn();
		String csvResponseHeader = result.getResponse().getContentAsString().split("\n")[0].trim();
		String csvHeaderFromProperty = victoryDataCsvHeader.trim();
		Assert.assertTrue(csvResponseHeader.equals(csvHeaderFromProperty));
	}

	@Test
	public void givenTypeParamAsJson_whenGetTop10Victories_thenResponseJson() throws Exception {
		mvc.perform(
				get("/victories").queryParam("start", startYear).queryParam("end", endYear).queryParam("type", "json"))
				.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

	@Test
	public void givenTypeParamAsJson_whenGetTop10Victories_thenValidResponseJson() throws Exception {
		mvc.perform(
				get("/victories").queryParam("start", startYear).queryParam("end", endYear).queryParam("type", "json"))
				.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(10))).andExpect(jsonPath("$[0].nationality", isA(String.class)))
				.andExpect(jsonPath("$[0].wins", isA(Integer.class)))
				.andExpect(jsonPath("$[0].rank", isA(Integer.class))).andExpect(jsonPath("$[0].rank", is(1)));
	}

}
