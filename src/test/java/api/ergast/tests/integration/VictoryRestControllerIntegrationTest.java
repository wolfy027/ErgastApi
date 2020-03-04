package api.ergast.tests.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
public class VictoryRestControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void givenInvalidYears_whenGetTop10Victories_thenStatus400() throws Exception {
		mvc.perform(get("/victories").contentType(MediaType.APPLICATION_JSON).queryParam("start", "2020")
				.queryParam("end", "2018").queryParam("type", "json")).andDo(print())
				.andExpect(status().isBadRequest());
	}

	@Test
	public void givenTypeParamAsJson_whenGetTop10Victories_thenResponseJson() throws Exception {
		mvc.perform(get("/victories").queryParam("start", "2009").queryParam("end", "2010").queryParam("type", "json"))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

	@Test
	public void givenTypeParamAsCsv_whenGetTop10Victories_thenResponseCsv() throws Exception {
		mvc.perform(get("/victories").queryParam("start", "2009").queryParam("end", "2010").queryParam("type", "csv"))
				.andDo(print()).andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith("text/csv"));
	}

}
