package com.safetynet.emergencysystem;

import com.safetynet.emergencysystem.service.person.PersonReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;

@SpringBootApplication
public class EmergencySystemApplication extends WebMvcConfigurerAdapter implements CommandLineRunner {

	@Autowired
	private PersonReadService personReadService;

	public static void main(String[] args) throws IOException {

		SpringApplication.run(EmergencySystemApplication.class, args);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {

		registry.addRedirectViewController("/", "/persons");
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("==============================");
		personReadService.initializeData("data.json");
		System.out.println("==============================");
	}
}
