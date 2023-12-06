package com.myschool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication()
public class ScoremanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScoremanagementApplication.class, args);
	}

}
