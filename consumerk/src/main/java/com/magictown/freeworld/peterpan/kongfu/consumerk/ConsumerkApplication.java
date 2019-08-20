package com.magictown.freeworld.peterpan.kongfu.consumerk;

import com.magictown.freeworld.peterpan.kongfu.consumerk.stream.VectorCloudStreamBroadcaster;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableCircuitBreaker
@EnableEurekaClient
@EnableCaching
@EnableHystrix
@EnableHystrixDashboard
@EnableBinding(value = {VectorCloudStreamBroadcaster.class})
public class ConsumerkApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerkApplication.class, args);
	}

}
