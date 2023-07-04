package com.trio.java.bikerentapi.mapper;

import com.trio.java.bikerentapi.data.BikeRent;
import com.trio.java.bikerentapi.data.BikeRentDetails;
import com.trio.java.bikerentapi.data.User;

public class BikeRentEntityMapper {

  public BikeRent fromBikeRentDetails(BikeRentDetails bikeRentDetails, User user) {
    return BikeRent.builder()
        .withBike(bikeRentDetails.getBike())
        .withUser(user)
        .withStartDate(bikeRentDetails.getStartDate())
        .withEndDate(bikeRentDetails.getEndDate())
        .withSubTotal(bikeRentDetails.getSubTotal())
        .withFee(bikeRentDetails.getFee())
        .withTotal(bikeRentDetails.getTotal())
        .build();
  }
}
