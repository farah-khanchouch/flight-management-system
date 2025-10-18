package com.example.flightmanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import io.github.cdimascio.dotenv.Dotenv;
@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class FlightManagementSystemApplication {

    public static void main(String[] args) {
        // Charger le fichier .env
    Dotenv dotenv = Dotenv.load();
    dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
        SpringApplication.run(FlightManagementSystemApplication.class, args);
    }
}
