package com.microfinance.transaction_services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TransactionServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionServicesApplication.class, args);
	}

}
