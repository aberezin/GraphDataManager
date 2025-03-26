package com.graphapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Main application class that starts the Spring Boot application.
 * We're excluding DataSourceAutoConfiguration because we'll set up our own datasource
 * in the DatabaseConfig class.
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableJpaRepositories(basePackages = "com.graphapp.repository.relational")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
