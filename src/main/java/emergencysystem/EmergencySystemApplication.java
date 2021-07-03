package emergencysystem;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import emergencysystem.controller.PersonController;
import emergencysystem.dao.FireStationRepository;
import emergencysystem.dao.MedicalRecordRepository;
import emergencysystem.dao.PersonRepository;
import emergencysystem.model.JsonData;
import emergencysystem.model.Person;
import emergencysystem.service.FireStationService;
import emergencysystem.service.JsonService;
import emergencysystem.service.MedicalRecordService;
import emergencysystem.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@SpringBootApplication
public class EmergencySystemApplication extends WebMvcConfigurerAdapter {

	public static void main(String[] args) throws IOException {

		SpringApplication.run(EmergencySystemApplication.class, args);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/", "/persons");
	}

}
