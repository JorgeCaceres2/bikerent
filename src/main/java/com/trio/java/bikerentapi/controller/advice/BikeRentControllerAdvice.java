package com.trio.java.bikerentapi.controller.advice;

import com.trio.java.bikerentapi.exception.BikeNotFoundException;
import com.trio.java.bikerentapi.exception.BikeRentException;
import com.trio.java.bikerentapi.exception.InvalidDateException;
import com.trio.java.bikerentapi.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BikeRentControllerAdvice {

  @ExceptionHandler({BikeNotFoundException.class, BikeRentException.class,
      InvalidDateException.class, UserNotFoundException.class})
  public ResponseEntity<String> handleException(Exception ex) {
    HttpStatus httpStatus;
    String errorMessage;

    if (ex instanceof BikeNotFoundException || ex instanceof UserNotFoundException) {
      httpStatus = HttpStatus.NOT_FOUND;
      errorMessage = ex.getMessage();
    } else if (ex instanceof InvalidDateException || ex instanceof BikeRentException) {
      httpStatus = HttpStatus.BAD_REQUEST;
      errorMessage = ex.getMessage();
    } else {
      httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
      errorMessage = "An error occurs. Please contact with customer service";
    }

    return ResponseEntity.status(httpStatus).body(errorMessage);
  }
}
