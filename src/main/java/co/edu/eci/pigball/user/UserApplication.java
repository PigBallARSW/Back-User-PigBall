package co.edu.eci.pigball.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class UserApplication {

	public static void main(String[] args) {

		// Load environment variables from .env file if it exists
		try {
			Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
			// Set system properties from .env file
			dotenv.entries().forEach(e -> System.setProperty(e.getKey(), e.getValue()));
		} catch (Exception e) {
			// Continue without .env file
		}

		// If SSL is disabled, ensure we're using HTTP
		if (Boolean.parseBoolean(System.getProperty("server.ssl.enabled", "false"))) {
			System.setProperty("server.port", "8445");
		} else {
			System.setProperty("server.port", "8082");
		}
		
		SpringApplication.run(UserApplication.class, args);
	}

}
