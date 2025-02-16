package com.qp.groceryapp;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableJpaAuditing
@OpenAPIDefinition(
		info = @Info(
				title = "Grocery application REST API Documentation",
				description = "Grocery application REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Nitikesh Pihul",
						email = "nitikeshpihul99@gmail.com"
				)
		)
)
public class GroceryappApplication {

	public static void main(String[] args) {
		SpringApplication.run(GroceryappApplication.class, args);
	}

}
