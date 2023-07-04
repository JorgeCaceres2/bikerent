package com.trio.java.bikerentapi.mapper;

import com.trio.java.bikerentapi.data.BikeRentDetails;
import com.trio.java.bikerentapi.dto.BikeRentDetailsDto;

public class BikeRentDetailsMapper {

  //TOdO: mapper inside a mapper??

  private final BikeMapper bikeMapper = new BikeMapper();

  public BikeRentDetailsDto fromBikeRentDetails(BikeRentDetails bikeRentDetails) {
    return BikeRentDetailsDto.builder()
        .withBikeDto(bikeMapper.fromBike(bikeRentDetails.getBike()))
        .withStartDate(bikeRentDetails.getStartDate())
        .withEndDate(bikeRentDetails.getEndDate())
        .withSubTotal(bikeRentDetails.getSubTotal())
        .withFee(bikeRentDetails.getFee())
        .withTotal(bikeRentDetails.getTotal())
        .build();
  }

}
