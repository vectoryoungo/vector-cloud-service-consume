package com.magictown.freeworld.peterpan.kongfu.consumerk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ConsumerkApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerkApplication.class, args);
	}

}
