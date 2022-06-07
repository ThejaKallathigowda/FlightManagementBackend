package com.flightApp.cts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;*/
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
/*@EnableSwagger2
@EnableCircuitBreaker
@EnableEurekaClient*/
public class FlightappBackendAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightappBackendAdminApplication.class, args);
	}
	
	/*@Bean
	@LoadBalanced
	RestTemplate getRestTemplate() {
		return new RestTemplate();
	}*/

}
