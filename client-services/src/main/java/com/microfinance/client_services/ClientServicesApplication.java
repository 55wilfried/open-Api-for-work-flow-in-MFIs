package com.microfinance.client_services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class ClientServicesApplication {


	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ClientServicesApplication.class);
		ConfigurableApplicationContext context = app.run(args);
		String contextPath = context.getEnvironment().getProperty("server.servlet.context-path");
		System.out.println("Context Path: " + contextPath);
	}

}
