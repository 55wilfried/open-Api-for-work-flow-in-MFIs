package com.microfinance.users_services;

import com.microfinance.users_services.service.UserServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UsersServicesApplicationTests {
	@Autowired
	private UserServices userServices;

	@Test
	void testGetAllCollecteur() {
		// Write test logic here
	}

	@Test
	void testGetAllCollectUser() {
		// Write test logic here
	}

	@Test
	void testGetAllCollectorByCodage() {
		// Write test logic here
	}

	@Test
	void testGetAllCollectUserByCodage() {
		// Write test logic here
	}

	// Add more test methods as needed
}

