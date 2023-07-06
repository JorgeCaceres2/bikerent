package com.trio.java.bikerentapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class BikeNotFoundException extends RuntimeException {

  public BikeNotFoundException(String message) {
    super(message);
  }
}
