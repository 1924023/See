package com.care.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.sql.SQLException;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EntityScan(basePackages = {"com.care.test"})
@EnableJpaRepositories(basePackages = {"com.care.test.member", "com.care.test.pay", "com.care.test.admin", "com.care.test.movie_list", "com.care.test.support"})

public class SeeApplication {
	public static void main(String[] args) throws SQLException {

		SpringApplication.run(SeeApplication.class, args);


	}
}
