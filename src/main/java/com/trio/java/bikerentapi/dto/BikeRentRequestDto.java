package com.trio.java.bikerentapi.dto;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;
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
public class BikeRentRequestDto {

  //TODO: is it working?

  @NotNull(message = "UserId is required")
  private int userId;
  @NotNull(message = "BikeId is required")
  private int bikeId;
  @NotNull(message = "StartDate is required")
  private LocalDate startDate;
  @NotNull(message = "EndDate is required")
  private LocalDate endDate;

}
