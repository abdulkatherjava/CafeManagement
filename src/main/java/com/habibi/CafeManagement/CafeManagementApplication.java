package com.habibi.CafeManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@EnableJdbcRepositories
public class CafeManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(CafeManagementApplication.class, args);
	}

}
