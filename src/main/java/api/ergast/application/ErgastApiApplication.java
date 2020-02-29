package api.ergast.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "api.ergast"} )
public class ErgastApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ErgastApiApplication.class, args);
	}

}
