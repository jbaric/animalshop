package sk.baric.animalshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Main class to start a project.
 * 
 * @author Juraj Baric
 *
 */
@SpringBootApplication
public class AnimalShopApplication {
	public static void main(String[] args) {
        SpringApplication.run(AnimalShopApplication.class, args);
    }
	
	@Bean
	public PasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder();
	}
}
