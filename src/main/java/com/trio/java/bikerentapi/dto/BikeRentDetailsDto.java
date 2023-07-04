package com.trio.java.bikerentapi.dto;

import java.time.LocalDate;
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
public class BikeRentDetailsDto {

  private BikeDto bikeDto;
  private LocalDate startDate;
  private LocalDate endDate;
  private double subTotal;
  private double fee;
  private double total;

}
