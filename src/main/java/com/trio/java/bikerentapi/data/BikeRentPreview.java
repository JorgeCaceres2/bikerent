package com.trio.java.bikerentapi.data;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
@ToString
public class BikeRentPreview {

  private Bike bike;
  private LocalDate startDate;
  private LocalDate endDate;
  private double subTotal;
  private double fee;
  private double total;

}
