package com.trio.java.bikerentapi.config;

import com.trio.java.bikerentapi.repository.BikeRepository;
import com.trio.java.bikerentapi.repository.impl.database.DatabaseBikeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.trio.java.bikerentapi")
public class WebConfig {

    @Bean
    BikeRepository bikeRepository() {
        return new DatabaseBikeRepository();
    }
}
