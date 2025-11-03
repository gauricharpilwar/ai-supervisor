package com.frontdesk.ai_supervisor;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableScheduling
public class AiSupervisorApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiSupervisorApplication.class, args);
	}

}
