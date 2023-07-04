package com.trio.java.bikerentapi.repository;

import com.trio.java.bikerentapi.data.Bike;
import com.trio.java.bikerentapi.data.BikeRent;
import java.time.LocalDate;
import java.util.List;

public interface BikeRentRepository {

  List<BikeRent> getBookedBikeRentsByBike(Bike bike, LocalDate startDate, LocalDate endDate);

  BikeRent saveBikeRent(BikeRent bikeRent);

}
