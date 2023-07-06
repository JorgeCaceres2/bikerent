package com.trio.java.bikerentapi.mapper;

import com.trio.java.bikerentapi.data.BikeRent;
import com.trio.java.bikerentapi.dto.DateRangeDto;
import java.util.List;

public class DateRangeMapper {

  public List<DateRangeDto> fromBikeRentList(List<BikeRent> bikeRentList) {
    if (bikeRentList == null || bikeRentList.isEmpty()) {
      return List.of();
    }

    return bikeRentList.stream()
        .map(rent -> new DateRangeDto(rent.getStartDate(), rent.getEndDate()))
        .toList();
  }

}
