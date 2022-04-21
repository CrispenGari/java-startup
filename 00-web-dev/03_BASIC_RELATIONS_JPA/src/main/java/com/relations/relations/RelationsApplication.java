package com.relations.relations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
public class RelationsApplication {
	public static void main(String[] args) {
		SpringApplication.run(RelationsApplication.class, args);
	}
}
