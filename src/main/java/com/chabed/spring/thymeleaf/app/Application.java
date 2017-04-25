package com.chabed.spring.thymeleaf.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages ={"com.chabed.spring.thymeleaf.config"})
public class Application {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
		System.out.println("Spring Boot web Application in run successfully.......");
	}

}