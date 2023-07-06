package com.trio.java.bikerentapi.service;

import static com.trio.java.bikerentapi.util.ObjectsFactory.createBike;
import static com.trio.java.bikerentapi.util.ObjectsFactory.createBikeRent;
import static com.trio.java.bikerentapi.util.ObjectsFactory.createBikeRentRequestDto;
import static com.trio.java.bikerentapi.util.ObjectsFactory.createUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:test-application.properties")
class BikeRentServiceTest {

  @MockBean
  private BikeRentRepository bikeRentRepository;

  @MockBean
  private BikeRepository bikeRepository;

  @MockBean
  private UserRepository userRepository;

  @Autowired
  private BikeRentService bikeRentService;

  @Test
  void shouldReturnBikeRentDetails() {
    when(bikeRepository.getBikeDetails(1)).thenReturn(Optional.of(createBike(1, "bike1")));
    LocalDate startDate = LocalDate.now();
    LocalDate endDate = startDate.plusDays(3);
    BikeRentPreview result = bikeRentService.getBikeRentPreview(1, startDate, endDate);
    assertEquals(1, result.getBike().getId());
    assertEquals(40.0, result.getSubTotal());
    assertEquals(6.0, result.getFee());
    assertEquals(46.0, result.getTotal());
  }

  @Test
  void shouldThrowInvalidDateException() {
    LocalDate startDate = LocalDate.now();
    LocalDate endDate = startDate.plusDays(-3);

    when(bikeRepository.getBikeDetails(1)).thenReturn(Optional.of(createBike(1, "bike1")));

    assertThrows(InvalidDateException.class,
        () -> bikeRentService.getBikeRentPreview(1, startDate, endDate));
  }

  @Test
  void shouldReturnEmptyBookedBikeRents() {
    LocalDate startDate = LocalDate.now();
    LocalDate endDate = startDate.plusDays(3);

    when(bikeRepository.getBikeDetails(1)).thenReturn(Optional.of(getBike1()));
    when(bikeRentRepository.getBookedBikeRentsByBike(any(), any(), any()))
        .thenReturn(new ArrayList<>());

    List<BikeRent> result = bikeRentService.getBikeBookedDates(1, startDate, endDate);
    assertTrue(result.isEmpty());
  }

  @Test
  void shouldReturnBookedBikeRents() {
    LocalDate startDate = LocalDate.now();
    LocalDate endDate = startDate.plusDays(3);

    when(bikeRepository.getBikeDetails(1)).thenReturn(Optional.of(getBike1()));
    when(bikeRentRepository.getBookedBikeRentsByBike(any(), any(), any())).thenReturn(List.of(
        createBikeRent(getUser1(), getBike1(), startDate, endDate, 97.0, 58.2, 446.2),
        createBikeRent(getUser2(), getBike2(), startDate, endDate, 100.0, 60.0, 460.0)));

    List<BikeRent> result = bikeRentService.getBikeBookedDates(1, startDate, endDate);
    assertEquals(2, result.size());
    assertEquals("bike1", result.get(0).getBike().getName());
    assertEquals("John", result.get(0).getUser().getFirstName());
    assertEquals("bike2", result.get(1).getBike().getName());
    assertEquals("Anthony", result.get(1).getUser().getFirstName());
  }

  @Test
  void shouldSaveBikeRent() {
    LocalDate startDate = LocalDate.now();
    LocalDate endDate = startDate.plusDays(3);

    when(bikeRepository.getBikeDetails(1)).thenReturn(Optional.of(getBike1()));
    when(userRepository.getUserById(1)).thenReturn(Optional.of(getUser1()));
    when(bikeRentRepository.saveBikeRent(any())).thenReturn(createBikeRent(getUser1(),
        getBike1(), startDate, endDate, 97.0, 58.2, 446.2));

    BikeRent result = bikeRentService.saveBikeRent(createBikeRentRequestDto(1, 1,
        startDate, endDate));
    assertEquals("bike1", result.getBike().getName());
    assertEquals("John", result.getUser().getFirstName());
    assertEquals(446.2, result.getTotal());
  }

  @Test
  void shouldThrowUserNotFoundException() {
    LocalDate startDate = LocalDate.now();
    LocalDate endDate = startDate.plusDays(3);
    BikeRentRequestDto bikeRentRequestDto = createBikeRentRequestDto(1, 1000, startDate, endDate);

    when(bikeRepository.getBikeDetails(1)).thenReturn(Optional.of(getBike1()));

    assertThrows(UserNotFoundException.class,
        () -> bikeRentService.saveBikeRent(bikeRentRequestDto));
  }

  @Test
  void shouldThrowBikeNotFoundException() {
    LocalDate startDate = LocalDate.now();
    LocalDate endDate = startDate.plusDays(3);
    BikeRentRequestDto bikeRentRequestDto = createBikeRentRequestDto(1000, 1, startDate, endDate);

    when(userRepository.getUserById(1)).thenReturn(Optional.of(getUser1()));

    assertThrows(BikeNotFoundException.class,
        () -> bikeRentService.saveBikeRent(bikeRentRequestDto));
  }

  @Test
  void shouldThrowBikeRentException() {
    LocalDate startDate = LocalDate.now();
    LocalDate endDate = startDate.plusDays(3);

    when(bikeRepository.getBikeDetails(1)).thenReturn(Optional.of(getBike1()));
    when(userRepository.getUserById(1)).thenReturn(Optional.of(getUser1()));
    when(bikeRentRepository.getBookedBikeRentsByBike(any(), any(), any())).thenReturn(List.of(
        createBikeRent(getUser1(), getBike1(), startDate, endDate, 97.0, 58.2, 446.2)));
    BikeRentRequestDto requestDto = createBikeRentRequestDto(1, 1, startDate, endDate);
    assertThrows(BikeRentException.class, () -> bikeRentService.saveBikeRent(requestDto));
  }

  private Bike getBike1() {
    return createBike(1, "bike1");
  }

  private Bike getBike2() {
    return createBike(2, "bike2");
  }

  private User getUser1() {
    return createUser(1, "John", "May");
  }

  private User getUser2() {
    return createUser(2, "Anthony", "Phoenix");
  }
}
