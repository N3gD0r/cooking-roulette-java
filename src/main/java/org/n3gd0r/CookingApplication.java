package org.n3gd0r;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class CookingApplication {
    public static void main(String[] args) {
        SpringApplication.run(CookingApplication.class, args);
    }
}
