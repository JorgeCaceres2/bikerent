package com.trio.java.bikerentapi.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class BikeBookedDates {

  private BikeDto bikeDto;
  private List<DateRangeDto> dateRangeDtoList;

}
