package com.dash;

import com.dash.models.User;
import com.dash.services.UserService;
import java.util.Arrays;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
public class DashApplication {

	public static void main(String[] args) {
		SpringApplication.run(DashApplication.class, args);
	}
       
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:4200"));
		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
				"Accept", "Jwt-Token", "Authorization", "Origin, Accept", "X-Requested-With",
				"Access-Control-Request-Method", "Access-Control-Request-Headers"));
		corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Jwt-Token", "Authorization",
				"Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "Filename"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}
	
	@Bean
	public CommandLineRunner dataSeeder(UserService userService) {
		return args -> {
			User user1 = new User();
			user1.setFullName("Mark Junior");
			user1.setPassword("password");
			user1.setEmail("mark@example.com");
			user1.setGender("Male");
                        user1.setDateOfBirth("1990-01-01");
			
			userService.createUser(user1);
			
			User user2 = new User();
			user2.setFullName("Becky Potter");
			user2.setPassword("password");
			user2.setEmail("becky@example.com");
			user1.setGender("Female");
                        user1.setDateOfBirth("2000-01-01");
			
			userService.createUser(user2);
			
			System.out.println("Data seeding completed!");
		};
	}
}
