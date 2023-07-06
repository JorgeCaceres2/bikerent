package com.trio.java.bikerentapi.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class RentConfig {

  @Value("${rent.fee:15}")
  private int fee;
}
