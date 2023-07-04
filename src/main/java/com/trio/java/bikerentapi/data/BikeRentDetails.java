package com.trio.java.bikerentapi.data;

import java.time.LocalDate;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class BikeRentDetails {

  //TODO: is ok that this is not a DTO?

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "bike_id")
  private Bike bike;
  private LocalDate startDate;
  private LocalDate endDate;
  private double subTotal;
  private double fee;
  private double total;

}
