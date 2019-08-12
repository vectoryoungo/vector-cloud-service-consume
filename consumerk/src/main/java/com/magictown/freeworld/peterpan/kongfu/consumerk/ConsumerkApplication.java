package com.magictown.freeworld.peterpan.kongfu.consumerk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableCircuitBreaker
@EnableEurekaClient
@EnableCaching
@EnableHystrix
@EnableHystrixDashboard
public class ConsumerkApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerkApplication.class, args);
	}

}
