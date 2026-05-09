package com.eazybytes.eazystore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.eazybytes.eazystore.dto.ContactInfoDto;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
@EnableCaching
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
@EnableConfigurationProperties(value = { ContactInfoDto.class })
// @EnableJpaRepositories
// @EntityScan
// @ComponentScan(basePackages = { "com.eazybytes.eazystore.controller" })
public class EazystoreApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure()
				.directory("./eazystore")
				.ignoreIfMissing()
				.load();
		String stripeKey = dotenv.get("STRIPE_API_KEY");

		if (stripeKey != null) {
			System.setProperty("STRIPE_API_KEY", stripeKey);
		}

		SpringApplication.run(EazystoreApplication.class, args);
	}
}
