package org.n3gd0r;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "org.n3gd0r.*")
@SpringBootApplication
public class CookingApplication {
    private static final Logger log = LoggerFactory.getLogger(CookingApplication.class);

    public static void main(String[] args) {
        log.info("Starting Cooking Roulette application...");
        SpringApplication.run(CookingApplication.class, args);
        log.info("Cooking Roulette application started successfully");
    }
}
