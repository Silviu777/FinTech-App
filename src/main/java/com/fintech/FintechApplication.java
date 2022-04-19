package com.fintech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.fintech.repository")
@SpringBootApplication
public class FintechApplication {

	public static void main(String[] args) {
		SpringApplication.run(FintechApplication.class, args);
	}

}
