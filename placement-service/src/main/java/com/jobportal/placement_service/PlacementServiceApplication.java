package com.jobportal.placement_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class PlacementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlacementServiceApplication.class, args);
	}

}
