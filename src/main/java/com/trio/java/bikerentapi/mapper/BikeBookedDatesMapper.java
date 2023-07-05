package com.trio.java.bikerentapi.mapper;

import com.trio.java.bikerentapi.data.BikeRent;
import com.trio.java.bikerentapi.dto.BikeBookedDates;
import com.trio.java.bikerentapi.dto.DateRangeDto;
import java.util.List;

public class BikeBookedDatesMapper {

  private BikeMapper bikeMapper = new BikeMapper();

  //TODO: it returns null instead of empty object

  public BikeBookedDates fromBikeRentList(List<BikeRent> bikeRentList) {
    if (bikeRentList == null || bikeRentList.isEmpty()) {
      return null;
    }

    List<DateRangeDto> dateRangeDtoList = bikeRentList.stream()
        .map(rent -> new DateRangeDto(rent.getStartDate(), rent.getEndDate()))
        .toList();

    return BikeBookedDates.builder()
        .withBikeDto(bikeMapper.fromBike(bikeRentList.get(0).getBike()))
        .withDateRangeDtoList(dateRangeDtoList)
        .build();
  }

}
