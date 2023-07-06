package com.trio.java.bikerentapi.util;

import com.trio.java.bikerentapi.data.Bike;
import com.trio.java.bikerentapi.data.BikeRent;
import com.trio.java.bikerentapi.data.BikeRentPreview;
import com.trio.java.bikerentapi.data.User;
import com.trio.java.bikerentapi.dto.BikeRentRequestDto;
import java.time.LocalDate;
import java.util.ArrayList;

public class ObjectsFactory {

  public static Bike createBike(int id, String name) {
    return Bike.builder()
        .withId(id)
        .withName(name)
        .withRate(10.0)
        .withImageUrls(new ArrayList<>())
        .build();
  }

  public static User createUser(int id, String name, String lastName) {
    return User.builder()
        .withId(id)
        .withFirstName(name)
        .withLastName(lastName)
        .build();
  }


  public static BikeRent createBikeRent(User user, Bike bike, LocalDate startDate,
      LocalDate endDate, double subtotal, double fee, double total) {
    return BikeRent.builder()
        .withUser(user)
        .withBike(bike)
        .withStartDate(startDate)
        .withEndDate(endDate)
        .withSubTotal(subtotal)
        .withFee(fee)
        .withTotal(total)
        .build();
  }

  public static BikeRentPreview createBikeRentDetails(Bike bike, LocalDate startDate,
      LocalDate endDate, double subtotal, double fee, double total) {
    return BikeRentPreview.builder()
        .withBike(bike)
        .withStartDate(startDate)
        .withEndDate(endDate)
        .withSubTotal(subtotal)
        .withFee(fee)
        .withTotal(total)
        .build();
  }

  public static BikeRentRequestDto createBikeRentRequestDto(int bikeId, int userId,
      LocalDate startDate, LocalDate endDate) {
    return BikeRentRequestDto.builder()
        .withBikeId(bikeId)
        .withUserId(userId)
        .withStartDate(startDate)
        .withEndDate(endDate)
        .build();
  }
}
