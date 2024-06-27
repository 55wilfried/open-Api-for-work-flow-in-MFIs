package com.microfinance.loans_services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LoansServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoansServicesApplication.class, args);
	}

}
