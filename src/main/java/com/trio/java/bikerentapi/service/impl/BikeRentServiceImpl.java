package com.trio.java.bikerentapi.service.impl;

import com.trio.java.bikerentapi.config.RentConfig;
import com.trio.java.bikerentapi.data.Bike;
import com.trio.java.bikerentapi.data.BikeRent;
import com.trio.java.bikerentapi.data.BikeRentPreview;
import com.trio.java.bikerentapi.data.User;
import com.trio.java.bikerentapi.dto.BikeRentRequestDto;
import com.trio.java.bikerentapi.exception.BikeNotFoundException;
import com.trio.java.bikerentapi.exception.BikeRentException;
import com.trio.java.bikerentapi.exception.InvalidDateException;
import com.trio.java.bikerentapi.exception.UserNotFoundException;
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

  @Override
  public BikeRentPreview getBikeRentPreview(int bikeId, LocalDate startDate,
      LocalDate endDate) {
    log.info("About to get a bike rent for bike:{}", bikeId);

    checkDates(startDate, endDate);

    Bike bike = getBike(bikeId);

    double subTotal = getSubTotal(startDate, endDate, bike.getRate());
    double feeTotal = getFee(subTotal);
    double total = getTotal(subTotal, feeTotal);
    return BikeRentPreview.builder()
        .withBike(bike)
        .withStartDate(startDate)
        .withEndDate(endDate)
        .withFee(feeTotal)
        .withSubTotal(subTotal)
        .withTotal(total)
        .build();
  }

  @Override
  public BikeRent saveBikeRent(BikeRentRequestDto bikeRentRequestDto) {
    int bikeId = bikeRentRequestDto.getBikeId();
    int userId = bikeRentRequestDto.getUserId();
    LocalDate startDate = bikeRentRequestDto.getStartDate();
    LocalDate endDate = bikeRentRequestDto.getEndDate();

    log.info("About to save a new bike rent for bike:{}, user:{}", bikeId, userId);

    checkDates(startDate, endDate);

    User user = getUser(userId);

    Bike bike = getBike(bikeId);

    checkBookedBikeRents(bike, startDate, endDate);

    double subTotal = getSubTotal(startDate, endDate, bike.getRate());
    double feeTotal = getFee(subTotal);
    double total = getTotal(subTotal, feeTotal);
    BikeRent bikeRent = BikeRent.builder()
        .withUser(user)
        .withBike(bike)
        .withStartDate(startDate)
        .withEndDate(endDate)
        .withFee(feeTotal)
        .withSubTotal(subTotal)
        .withTotal(total)
        .build();

    return bikeRentRepository.saveBikeRent(bikeRent);
  }

  @Override
  public List<BikeRent> getBikeBookedDates(int bikeId, LocalDate startDate,
      LocalDate endDate) {
    log.info("About to search booked rents for bikeId:{}, "
        + "startDate:{}, endDate:{}", bikeId, startDate, endDate);

    checkDates(startDate, endDate);

    Bike bike = getBike(bikeId);

    return getBikeRentByBike(bike, startDate, endDate);
  }

  private List<BikeRent> getBikeRentByBike(Bike bike, LocalDate startDate,
      LocalDate endDate) {
    return bikeRentRepository.getBookedBikeRentsByBike(bike, startDate, endDate);
  }

  private Bike getBike(int bikeId) {
    log.info("Checking bike with Id:{}", bikeId);
    Optional<Bike> bike = bikeRepository.getBikeDetails(bikeId);
    if (bike.isEmpty()) {
      String errorMsg = "Bike not found";
      log.error(errorMsg.concat(". Id:{}"), bikeId);
      throw new BikeNotFoundException(errorMsg);
    }
    return bike.get();
  }

  private User getUser(int userId) {
    log.info("Checking user with Id:{}", userId);
    Optional<User> user = userRepository.getUserById(userId);
    if (user.isEmpty()) {
      String errorMsg = "User not found";
      log.error(errorMsg.concat(". Id:{}"), userId);
      throw new UserNotFoundException(errorMsg);
    }
    return user.get();
  }

  private void checkDates(LocalDate startDate, LocalDate endDate) {
    log.info("Checking dates. StartDate:{}, endDate:{}", startDate, endDate);
    if (startDate == null || endDate == null
        || startDate.isBefore(LocalDate.now()) || endDate.isBefore(startDate)) {
      String errorMsg = "Invalid dates provided";
      log.error(errorMsg.concat(". StartDate:{}, endDate:{}"), startDate, endDate);
      throw new InvalidDateException(errorMsg);
    }
  }

  private void checkBookedBikeRents(Bike bike, LocalDate startDate, LocalDate endDate) {
    log.info("Checking bike rent availability for Bike:{}", bike.getId());
    if (!getBikeRentByBike(bike, startDate, endDate).isEmpty()) {
      String errorMsg = "There are booked rents for the chosen dates";
      log.error(errorMsg.concat(". BikeId:{}, startDate:{}, EndDate:{}"),
          bike.getId(), startDate, endDate);
      throw new BikeRentException(errorMsg);
    }
  }

  private double getSubTotal(LocalDate startDate, LocalDate endDate, double rate) {
    long days = ChronoUnit.DAYS.between(startDate, endDate) + 1;
    return rate * days;
  }

  private double getFee(double subtotal) {
    return subtotal * rentConfig.getFee() / 100;
  }

  private double getTotal(double subtotal, double fee) {
    return subtotal + fee;
  }
}
