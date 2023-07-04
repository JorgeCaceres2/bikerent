package com.trio.java.bikerentapi.dto;

import com.trio.java.bikerentapi.data.Bike;
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
public class BookedBikeRentDto {

  private BikeDto bikeDto;
  private List<DateRangeDto> dateRangeDtoList;

}
