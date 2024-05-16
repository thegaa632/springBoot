package com.boot.s1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringBootStApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootStApplication.class, args);
	}

}
