package com.trio.java.bikerentapi.dto;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BikeRentRequestDto {

  @NotNull(message = "UserId is required")
  private int userId;
  @NotNull(message = "BikeId is required")
  private int bikeId;
  @NotNull(message = "StartDate is required")
  private LocalDate startDate;
  @NotNull(message = "EndDate is required")
  private LocalDate endDate;

}
