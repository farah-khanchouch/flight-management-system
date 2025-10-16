package com.example.flightmanagementsystem.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
@Data
public class AppProperties {
    private final Jwt jwt = new Jwt();

    @Data
    public static class Jwt {
        private String secret = "bXlTZWNyZXRKd3RTZWNyZXRLZXl0aGF0SXNOb3RTb1NlY3JldE9ySXNJdD8=";
        private long expiration = 86400000; // 24 heures en millisecondes
    }
}
