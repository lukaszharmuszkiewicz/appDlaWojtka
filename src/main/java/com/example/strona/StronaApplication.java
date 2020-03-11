package com.example.strona;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StronaApplication {

	@Autowired
	private ExcelService excelService;

	public static void main(String[] args) {

		SpringApplication.run(StronaApplication.class, args);

	}

}
