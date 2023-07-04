package com.trio.java.bikerentapi.controller;

import com.trio.java.bikerentapi.dto.BikeRentDetailsDto;
import com.trio.java.bikerentapi.dto.BikeRentDto;
import com.trio.java.bikerentapi.dto.BikeRentRequestDto;
import com.trio.java.bikerentapi.dto.BookedBikeRentDto;
import com.trio.java.bikerentapi.mapper.BikeRentDetailsMapper;
import com.trio.java.bikerentapi.mapper.BikeRentMapper;
import com.trio.java.bikerentapi.mapper.BookedBikeRentMapper;
import com.trio.java.bikerentapi.service.BikeRentService;
import java.time.LocalDate;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/bikes/rent")
public class BikeRentController {

  @Autowired
  private BikeRentService bikeRentService;

  //TODO: is a good practice to have 3 mappers here?
  //TODO: review url's

  private BikeRentDetailsMapper bikeRentDetailsMapper = new BikeRentDetailsMapper();
  private BookedBikeRentMapper bookedBikeRentMapper = new BookedBikeRentMapper();
  private BikeRentMapper bikeRentMapper = new BikeRentMapper();

  @GetMapping(value = "details/{bike-id}")
  public BikeRentDetailsDto getRentDetails(@PathVariable("bike-id") int id,
      @RequestParam(value = "start-date")
      @NotNull @DateTimeFormat(iso = ISO.DATE) LocalDate startDate,
      @RequestParam(value = "end-date")
      @NotNull @DateTimeFormat(iso = ISO.DATE) LocalDate endDate) {

    return bikeRentDetailsMapper.fromBikeRentDetails(bikeRentService
        .getBikeRentDetails(id, startDate, endDate));
  }

  @GetMapping(value = "booked/{bike-id}")
  public BookedBikeRentDto getBookedBikeRentDtoByBike(@PathVariable("bike-id") int id,
      @RequestParam(value = "start-date")
      @NotNull @DateTimeFormat(iso = ISO.DATE) LocalDate startDate,
      @RequestParam(value = "end-date")
      @NotNull @DateTimeFormat(iso = ISO.DATE) LocalDate endDate) {

    //TODO: This returns {"bikeDto":null,"dateRangeDtoList":null}
    // IMO is better to return bikeDto filled, but empty dateRanges
    return bookedBikeRentMapper.fromBikeRentList(bikeRentService
        .getBookedBikeRentsByBike(id, startDate, endDate));
  }

  @PostMapping
  public BikeRentDto rentBike(@Valid @RequestBody BikeRentRequestDto bikeRentRequestDto) {
    return bikeRentMapper.fromBikeRent(bikeRentService
        .saveBikeRent(bikeRentRequestDto));
  }
}
