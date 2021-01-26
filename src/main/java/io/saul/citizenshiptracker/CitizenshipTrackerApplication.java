package io.saul.citizenshiptracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CitizenshipTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CitizenshipTrackerApplication.class, args);
    }

}
