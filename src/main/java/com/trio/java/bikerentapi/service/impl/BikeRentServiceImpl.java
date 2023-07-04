package com.trio.java.bikerentapi.service.impl;

import com.trio.java.bikerentapi.config.RentConfig;
import com.trio.java.bikerentapi.data.Bike;
import com.trio.java.bikerentapi.data.BikeRent;
import com.trio.java.bikerentapi.data.BikeRentDetails;
import com.trio.java.bikerentapi.data.User;
import com.trio.java.bikerentapi.dto.BikeRentRequestDto;
import com.trio.java.bikerentapi.exception.BikeNotFoundException;
import com.trio.java.bikerentapi.exception.BikeRentException;
import com.trio.java.bikerentapi.exception.InvalidDateException;
import com.trio.java.bikerentapi.exception.UserNotFoundException;
import com.trio.java.bikerentapi.mapper.BikeRentEntityMapper;
import com.trio.java.bikerentapi.repository.BikeRentRepository;
import com.trio.java.bikerentapi.repository.BikeRepository;
import com.trio.java.bikerentapi.repository.UserRepository;
import com.trio.java.bikerentapi.service.BikeRentService;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BikeRentServiceImpl implements BikeRentService {

  @Autowired
  private RentConfig rentConfig;

  @Autowired
  private BikeRepository bikeRepository;

  @Autowired
  private BikeRentRepository bikeRentRepository;

  @Autowired
  private UserRepository userRepository;

  private final BikeRentEntityMapper bikeRentEntityMapper = new BikeRentEntityMapper();


  @Override
  public BikeRentDetails getBikeRentDetails(int id, LocalDate startDate,
      LocalDate endDate) {

    Bike bike = getBike(id);
    checkDates(startDate, endDate);
    return createBikeRentDetails(startDate, endDate, bike);

  }

  @Override
  public BikeRent saveBikeRent(BikeRentRequestDto bikeRentRequestDto) {
    log.info("Attempting to save a new Bike Rent");

    checkBookedBikeRents(bikeRentRequestDto);

    checkDates(bikeRentRequestDto.getStartDate(), bikeRentRequestDto.getEndDate());

    User user = getUser(bikeRentRequestDto.getUserId());

    BikeRentDetails bikeRentDetails = getBikeRentDetails(bikeRentRequestDto.getBikeId(),
        bikeRentRequestDto.getStartDate(), bikeRentRequestDto.getEndDate());

    BikeRent bikeRent = bikeRentEntityMapper.fromBikeRentDetails(bikeRentDetails, user);

    return bikeRentRepository.saveBikeRent(bikeRent);

  }

  @Override
  public List<BikeRent> getBookedBikeRentsByBike(int id, LocalDate startDate, LocalDate endDate) {
    log.info("About to search booked rents for bikeId={}, startDate={}, "
        + "endDate={}", id, startDate, endDate);

    Bike bike = getBike(id);
    checkDates(startDate, endDate);
    return bikeRentRepository.getBookedBikeRentsByBike(bike, startDate, endDate);
  }

  private Bike getBike(int id) {
    Optional<Bike> bike = bikeRepository.getBikeDetails(id);
    if (bike.isEmpty()) {
      String errorMsg = String.format("Bike with Id = %s not found", id);
      log.error(errorMsg);
      throw new BikeNotFoundException(errorMsg);
    }
    return bike.get();
  }

  private User getUser(int userId) {
    Optional<User> user = userRepository.getUserById(userId);
    if (user.isEmpty()) {
      String errorMsg = String.format("User with Id=%d not found", userId);
      log.error(errorMsg);
      throw new UserNotFoundException(errorMsg);
    }
    return user.get();
  }

  private void checkDates(LocalDate startDate, LocalDate endDate) {
    if (startDate == null || endDate == null
        || startDate.isBefore(LocalDate.now()) || endDate.isBefore(startDate)) {
      String errorMsg = "Invalid dates provided";
      log.error(errorMsg.concat(". StartDate:{}, endDate:{}"), startDate, endDate);
      throw new InvalidDateException(errorMsg);
    }
  }

  private void checkBookedBikeRents(BikeRentRequestDto bikeRentRequestDto) {
    if (!getBookedBikeRentsByBike(bikeRentRequestDto.getBikeId(), bikeRentRequestDto.getStartDate(),
        bikeRentRequestDto.getEndDate()).isEmpty()) {
      String errorMsg = "There are booked rents for the chosen dates";
      log.error(errorMsg.concat(". BikeId:{}, startDate: {}, EndDate: {}"),
          bikeRentRequestDto.getBikeId(), bikeRentRequestDto.getStartDate(),
          bikeRentRequestDto.getEndDate());
      throw new BikeRentException(errorMsg);
    }
  }

  private BikeRentDetails createBikeRentDetails(LocalDate startDate, LocalDate endDate, Bike bike) {
    long days = ChronoUnit.DAYS.between(startDate, endDate) + 1;
    double subTotal = bike.getRate() * days;
    double feeTotal = subTotal * rentConfig.getFee() / 100;
    double total = subTotal + feeTotal;

    BikeRentDetails bikeRentDetails = BikeRentDetails.builder()
        .withBike(bike)
        .withStartDate(startDate)
        .withEndDate(endDate)
        .withFee(feeTotal)
        .withSubTotal(subTotal)
        .withTotal(total)
        .build();

    log.info("Returning Bike rent details: {}", bikeRentDetails);
    return bikeRentDetails;
  }
}
