package agh.iot.waterit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
		"agh.iot.waterit.model.jpa"
})
@EnableJpaRepositories
public class WaterItMiddlewareApplication {

	public static void main(String[] args) {
		SpringApplication.run(WaterItMiddlewareApplication.class, args);
	}

}
