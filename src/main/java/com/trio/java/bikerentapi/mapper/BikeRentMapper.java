package com.trio.java.bikerentapi.mapper;

import com.trio.java.bikerentapi.data.BikeRent;
import com.trio.java.bikerentapi.dto.BikeRentDto;

public class BikeRentMapper {

  private final BikeMapper bikeMapper = new BikeMapper();
  private final UserMapper userMapper = new UserMapper();

  public BikeRentDto fromBikeRent(BikeRent bikeRent) {
    return BikeRentDto.builder()
        .withBikeDto(bikeMapper.fromBike(bikeRent.getBike()))
        .withUserDto(userMapper.fromUser(bikeRent.getUser()))
        .withStartDate(bikeRent.getStartDate())
        .withEndDate(bikeRent.getEndDate())
        .withSubTotal(bikeRent.getSubTotal())
        .withFee(bikeRent.getFee())
        .withTotal(bikeRent.getTotal())
        .build();
  }
}
