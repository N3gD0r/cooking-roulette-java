package org.n3gd0r.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CookingConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
