package com.example.demo;

import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class LoginPageApplication {
	private static final Logger logger = LoggerFactory.getLogger(LoginPageApplication.class);

	public static void main(String[] args) {
		String dbHost = System.getenv("DB_HOST");
		String dbUsername = System.getenv("DB_USERNAME");
		String dbPassword = System.getenv("DB_PASSWORD");

		logger.info("DB_HOST: {}", dbHost);
		logger.info("DB_USERNAME: {}", dbUsername);
		logger.info("DB_PASSWORD: {}", dbPassword);

		SpringApplication.run(LoginPageApplication.class, args);
	}

}
