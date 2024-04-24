package com.example.springframework.spring6rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Spring6RestApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Spring6RestApplication.class, args);
		System.out.println("Hello World");
	}

}
