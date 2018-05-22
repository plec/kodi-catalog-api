package com.plec.kodi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication

@EnableJpaRepositories("com.plec.kodi.repository")
@EntityScan("com.plec.kodi.entity")
public class ApiKodiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiKodiApplication.class, args);
	}
}
