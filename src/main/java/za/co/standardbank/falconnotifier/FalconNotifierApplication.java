package za.co.standardbank.falconnotifier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FalconNotifierApplication {

	public static void main(String[] args) {
		SpringApplication.run(FalconNotifierApplication.class, args);
	}

}
