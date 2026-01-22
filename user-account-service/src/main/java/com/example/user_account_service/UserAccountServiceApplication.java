package com.example.user_account_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableMethodSecurity
@SpringBootApplication
public class UserAccountServiceApplication {

	public static void main(String[] args) {

        SpringApplication.run(UserAccountServiceApplication.class, args);
	}

}
