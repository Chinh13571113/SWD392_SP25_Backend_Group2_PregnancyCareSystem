package com.swd.pregnancycare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PregrancycareApplication {

	public static void main(String[] args) {
		SpringApplication.run(PregrancycareApplication.class, args);

	}
}
