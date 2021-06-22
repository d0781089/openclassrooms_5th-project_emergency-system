package emergencysystem;

import emergencysystem.service.JsonService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;

@SpringBootApplication
public class EmergencySystemApplication extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(EmergencySystemApplication.class, args);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/", "/persons");
	}

}
