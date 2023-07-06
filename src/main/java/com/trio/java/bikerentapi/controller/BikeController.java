package com.trio.java.bikerentapi.controller;

import com.trio.java.bikerentapi.dto.BikeDto;
import com.trio.java.bikerentapi.dto.DateRangeDto;
import com.trio.java.bikerentapi.exception.BikeNotFoundException;
import com.trio.java.bikerentapi.mapper.BikeMapper;
import com.trio.java.bikerentapi.mapper.DateRangeMapper;
import com.trio.java.bikerentapi.service.BikeRentService;
import com.trio.java.bikerentapi.service.BikeService;
import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/bikes")
public class BikeController {

  @Autowired
  private BikeService bikeService;

  @Autowired
  private BikeRentService bikeRentService;

  private BikeMapper bikeMapper = new BikeMapper();

  private DateRangeMapper dateRangeMapper = new DateRangeMapper();

  @GetMapping
  public List<BikeDto> findAll() {
    return bikeService.getAllBikes().stream()
        .map(b -> bikeMapper.fromBike(b))
        .toList();
  }

  @GetMapping(value = "/{id}")
  public BikeDto findById(@PathVariable("id") int id) {
    return bikeMapper.fromBike(bikeService.getBikeDetails(id)
        .orElseThrow(() -> new BikeNotFoundException("Bike not found")));
  }

  @GetMapping(value = "{id}/booked-dates")
  public List<DateRangeDto> getBookedBikeRentDtoByBike(@PathVariable("id") int id,
      @RequestParam(value = "start-date")
      @NotNull @DateTimeFormat(iso = ISO.DATE) LocalDate startDate,
      @RequestParam(value = "end-date")
      @NotNull @DateTimeFormat(iso = ISO.DATE) LocalDate endDate) {

    return dateRangeMapper.fromBikeRentList(bikeRentService
        .getBikeBookedDates(id, startDate, endDate));
  }
}
