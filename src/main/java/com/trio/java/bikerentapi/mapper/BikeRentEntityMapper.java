package com.trio.java.bikerentapi.mapper;

import com.trio.java.bikerentapi.data.BikeRent;
import com.trio.java.bikerentapi.data.BikeRentPreview;
import com.trio.java.bikerentapi.data.User;

public class BikeRentEntityMapper {

  public BikeRent fromBikeRentDetails(BikeRentPreview bikeRentPreview, User user) {
    return BikeRent.builder()
        .withBike(bikeRentPreview.getBike())
        .withUser(user)
        .withStartDate(bikeRentPreview.getStartDate())
        .withEndDate(bikeRentPreview.getEndDate())
        .withSubTotal(bikeRentPreview.getSubTotal())
        .withFee(bikeRentPreview.getFee())
        .withTotal(bikeRentPreview.getTotal())
        .build();
  }
}
