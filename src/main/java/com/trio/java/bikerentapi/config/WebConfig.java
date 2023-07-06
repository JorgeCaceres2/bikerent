package com.trio.java.bikerentapi.config;

import com.trio.java.bikerentapi.repository.BikeRentRepository;
import com.trio.java.bikerentapi.repository.BikeRepository;
import com.trio.java.bikerentapi.repository.UserRepository;
import com.trio.java.bikerentapi.repository.impl.database.DatabaseBikeRentRepository;
import com.trio.java.bikerentapi.repository.impl.database.DatabaseBikeRepository;
import com.trio.java.bikerentapi.repository.impl.database.DatabaseUserRepository;
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

  @Bean
  BikeRentRepository bikeRentRepository() {
    return new DatabaseBikeRentRepository();
  }

  @Bean
  UserRepository userRepository() {
    return new DatabaseUserRepository();
  }
}
