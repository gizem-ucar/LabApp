package com.project.labapp;

import com.project.labapp.config.WebMvcConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(WebMvcConfig.class)
public class LabappApplication {

	public static void main(String[] args) {
		SpringApplication.run(LabappApplication.class, args);
	}

}
