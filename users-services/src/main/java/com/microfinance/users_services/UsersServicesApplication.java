package com.microfinance.users_services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UsersServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersServicesApplication.class, args);
	}

}
