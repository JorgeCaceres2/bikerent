package com.trio.java.bikerentapi.controller;

import com.trio.java.bikerentapi.dto.BikeRentDto;
import com.trio.java.bikerentapi.dto.BikeRentPreviewDto;
import com.trio.java.bikerentapi.dto.BikeRentRequestDto;
import com.trio.java.bikerentapi.mapper.BikeRentDetailsMapper;
import com.trio.java.bikerentapi.mapper.BikeRentMapper;
import com.trio.java.bikerentapi.service.BikeRentService;
import java.time.LocalDate;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/rents")
public class BikeRentController {

  @Autowired
  private BikeRentService bikeRentService;

  private BikeRentDetailsMapper bikeRentDetailsMapper = new BikeRentDetailsMapper();

  private BikeRentMapper bikeRentMapper = new BikeRentMapper();


  @GetMapping(value = "preview")
  public BikeRentPreviewDto getRentPreview(@RequestParam("bike-id") @NotNull int id,
      @RequestParam(value = "start-date")
      @NotNull @DateTimeFormat(iso = ISO.DATE) LocalDate startDate,
      @RequestParam(value = "end-date")
      @NotNull @DateTimeFormat(iso = ISO.DATE) LocalDate endDate) {

    return bikeRentDetailsMapper.fromBikeRentPreview(bikeRentService
        .getBikeRentPreview(id, startDate, endDate));
  }

  @PostMapping
  public BikeRentDto rentBike(@Valid @RequestBody BikeRentRequestDto bikeRentRequestDto) {
    return bikeRentMapper.fromBikeRent(bikeRentService
        .saveBikeRent(bikeRentRequestDto));
  }
}
