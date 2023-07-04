package com.trio.java.bikerentapi.mapper;

import com.trio.java.bikerentapi.data.BikeRent;
import com.trio.java.bikerentapi.dto.BookedBikeRentDto;
import com.trio.java.bikerentapi.dto.DateRangeDto;
import java.util.List;

public class BookedBikeRentMapper {

  //TODO: mapper inside a mapper

  private BikeMapper bikeMapper = new BikeMapper();

  public BookedBikeRentDto fromBikeRentList(List<BikeRent> bikeRentList) {
    if (bikeRentList == null || bikeRentList.isEmpty()) {
      return new BookedBikeRentDto();
    }

    List<DateRangeDto> dateRangeDtoList = bikeRentList.stream()
        .map(rent -> new DateRangeDto(rent.getStartDate(), rent.getEndDate()))
        .toList();

    return BookedBikeRentDto.builder()
        .withBikeDto(bikeMapper.fromBike(bikeRentList.get(0).getBike()))
        .withDateRangeDtoList(dateRangeDtoList)
        .build();
  }

}
