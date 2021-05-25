package com.elkard.tripmanager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TripManagerApplication {

	private static final Logger log = LoggerFactory.getLogger(TripManagerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TripManagerApplication.class);
	}

}
