package com.vcs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PractiseCustomerLoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(PractiseCustomerLoginApplication.class, args);
	}

}
