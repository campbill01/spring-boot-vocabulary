package com.campbell.vocabulary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VocabularyApplication {

	private static final String COMMA_DELIMITER = ",";

	public static void main(String[] args) {
		SpringApplication.run(VocabularyApplication.class, args);
	}

}
