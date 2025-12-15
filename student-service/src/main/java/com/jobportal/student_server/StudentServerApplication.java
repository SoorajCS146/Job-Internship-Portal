package com.jobportal.student_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class StudentServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentServerApplication.class, args);
	}

}
