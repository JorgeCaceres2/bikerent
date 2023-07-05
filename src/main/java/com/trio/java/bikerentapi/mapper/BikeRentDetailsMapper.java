package com.trio.java.bikerentapi.mapper;

import com.trio.java.bikerentapi.data.BikeRentPreview;
import com.trio.java.bikerentapi.dto.BikeRentPreviewDto;

public class BikeRentDetailsMapper {

  private final BikeMapper bikeMapper = new BikeMapper();

  public BikeRentPreviewDto fromBikeRentPreview(BikeRentPreview bikeRentPreview) {
    return BikeRentPreviewDto.builder()
        .withBikeDto(bikeMapper.fromBike(bikeRentPreview.getBike()))
        .withStartDate(bikeRentPreview.getStartDate())
        .withEndDate(bikeRentPreview.getEndDate())
        .withSubTotal(bikeRentPreview.getSubTotal())
        .withFee(bikeRentPreview.getFee())
        .withTotal(bikeRentPreview.getTotal())
        .build();
  }

}
