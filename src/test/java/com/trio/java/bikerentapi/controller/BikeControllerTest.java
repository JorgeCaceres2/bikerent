package com.trio.java.bikerentapi.controller;

import static com.trio.java.bikerentapi.util.ObjectsFactory.createBike;
import static com.trio.java.bikerentapi.util.ObjectsFactory.createBikeRent;
import static com.trio.java.bikerentapi.util.ObjectsFactory.createUser;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.trio.java.bikerentapi.data.Bike;
import com.trio.java.bikerentapi.data.User;
import com.trio.java.bikerentapi.service.BikeRentService;
import com.trio.java.bikerentapi.service.BikeService;
import com.trio.java.bikerentapi.util.ObjectsFactory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(controllers = BikeController.class)
class BikeControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BikeService bikeService;

  @MockBean
  private BikeRentService bikeRentService;

  @Test
  void shouldReturnEmptyListIfNoBikesPresent() throws Exception {
    when(bikeService.getAllBikes()).thenReturn(new ArrayList<>());

    this.mockMvc.perform(
            get("/api/bikes")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(0)));
  }

  @Test
  void shouldReturnListWithBikes() throws Exception {
    when(bikeService.getAllBikes()).thenReturn(Arrays.asList(
        ObjectsFactory.createBike(1, "Bike1"),
        ObjectsFactory.createBike(2, "Bike2")
    ));

    this.mockMvc.perform(
            get("/api/bikes")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].id", equalTo(1)))
        .andExpect(jsonPath("$[0].name", equalTo("Bike1")))
        .andExpect(jsonPath("$[1].id", equalTo(2)))
        .andExpect(jsonPath("$[1].name", equalTo("Bike2")));
  }

  @Test
  void shouldReturnBikeDetailsIfPresent() throws Exception {
    int id = 308;

    when(bikeService.getBikeDetails(id))
        .thenReturn(Optional.of(ObjectsFactory.createBike(id, "Some bike")));

    this.mockMvc.perform(
            get(String.format("/api/bikes/%s", id))
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", equalTo(id)))
        .andExpect(jsonPath("$.name", equalTo("Some bike")));
  }

  @Test
  void shouldReturnErrorIfBikeNotFound() throws Exception {
    int id = 404;

    when(bikeService.getBikeDetails(id)).thenReturn(Optional.empty());

    this.mockMvc.perform(
            get(String.format("/api/bikes/%s", id))
        )
        .andExpect(status().isNotFound());
  }

  @Test
  void shouldReturnBookedDates() throws Exception {
    Bike bike = getBike1();

    when(bikeRentService.getBikeBookedDates(anyInt(), any(), any())).thenReturn(
        List.of(createBikeRent(getUser1(), bike, LocalDate.now(),
            LocalDate.now().plusDays(3), 97.0, 58.2, 446.2)));

    mockMvc.perform(get(String.format("/api/bikes/%s/booked-dates", bike.getId()))
            .param("start-date", LocalDate.now().toString())
            .param("end-date", LocalDate.now().plusDays(3).toString()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isNotEmpty());
  }

  @Test
  void shouldReturnEmptyBookedDates() throws Exception {
    int id = 1;

    when(bikeRentService.getBikeBookedDates(anyInt(), any(), any())).thenReturn(List.of());

    mockMvc.perform(get(String.format("/api/bikes/%s/booked-dates", id))
            .param("start-date", LocalDate.now().toString())
            .param("end-date", LocalDate.now().plusDays(3).toString()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.dateRangeDtoList").doesNotExist());
  }

  private User getUser1() {
    return createUser(1, "John", "May");
  }

  private Bike getBike1() {
    return createBike(1, "bike1");
  }
}
