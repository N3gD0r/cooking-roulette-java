package org.n3gd0r;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "org.n3gd0r.*")
@SpringBootApplication
public class CookingApplication {
    public static void main(String[] args) {
        SpringApplication.run(CookingApplication.class, args);
    }
}
