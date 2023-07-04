package com.trio.java.bikerentapi.service;

import com.trio.java.bikerentapi.data.BikeRent;
import com.trio.java.bikerentapi.data.BikeRentDetails;
import com.trio.java.bikerentapi.dto.BikeRentRequestDto;
import java.time.LocalDate;
import java.util.List;

public interface BikeRentService {

  BikeRentDetails getBikeRentDetails(int id, LocalDate startDate, LocalDate endDate);

  BikeRent saveBikeRent(BikeRentRequestDto bikeRentRequestDto);

  List<BikeRent> getBookedBikeRentsByBike(int id, LocalDate startDate, LocalDate endDate);

}
