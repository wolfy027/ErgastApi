package api.ergast.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/")
public class ErgastServiceController {

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Autowired
	RestTemplate restTemplate;

	@Value("${ergast.baseUrl}")
	String serviceURL;

	@GetMapping("welcome")
	public String welcome() {
		return restTemplate.getForObject(serviceURL + "/api/f1/drivers.json", String.class);
	}

	@GetMapping("doc")
	public String documentation() {
		return restTemplate.getForObject(serviceURL + "/mrd/", String.class);
	}

}

@Component
class WelcomeService {

	public String welcomeMessage() {
		return "Welcome Team A";
	}
}
