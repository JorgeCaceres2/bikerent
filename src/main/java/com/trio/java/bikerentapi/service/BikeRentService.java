package com.trio.java.bikerentapi.service;

import com.trio.java.bikerentapi.data.BikeRent;
import com.trio.java.bikerentapi.data.BikeRentPreview;
import com.trio.java.bikerentapi.dto.BikeRentRequestDto;
import java.time.LocalDate;
import java.util.List;

public interface BikeRentService {

  BikeRentPreview getBikeRentPreview(int bikeId, LocalDate startDate, LocalDate endDate);

  BikeRent saveBikeRent(BikeRentRequestDto bikeRentRequestDto);

  List<BikeRent> getBikeBookedDates(int bikeId, LocalDate startDate, LocalDate endDate);

}
