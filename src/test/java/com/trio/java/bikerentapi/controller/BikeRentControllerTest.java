package com.trio.java.bikerentapi.controller;

import static com.trio.java.bikerentapi.util.ObjectsFactory.createBike;
import static com.trio.java.bikerentapi.util.ObjectsFactory.createBikeRent;
import static com.trio.java.bikerentapi.util.ObjectsFactory.createBikeRentDetails;
import static com.trio.java.bikerentapi.util.ObjectsFactory.createBikeRentRequestDto;
import static com.trio.java.bikerentapi.util.ObjectsFactory.createUser;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.trio.java.bikerentapi.data.Bike;
import com.trio.java.bikerentapi.data.User;
import com.trio.java.bikerentapi.exception.BikeNotFoundException;
import com.trio.java.bikerentapi.exception.BikeRentException;
import com.trio.java.bikerentapi.exception.InvalidDateException;
import com.trio.java.bikerentapi.exception.UserNotFoundException;
import com.trio.java.bikerentapi.service.BikeRentService;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = BikeRentController.class)
class BikeRentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BikeRentService bikeRentService;

  private final ObjectMapper objectMapper = new ObjectMapper();

  private final int bikeId = 1;

  private final int userId = 1;

  @Test
  void shouldReturnBikeRentPreview() throws Exception {
    LocalDate startDate = LocalDate.now();
    LocalDate endDate = startDate.plusDays(3);
    when(bikeRentService.getBikeRentPreview(1, startDate, endDate))
        .thenReturn(createBikeRentDetails(getBike1(), startDate,
            endDate, 97.0, 58.2, 446.2));

    mockMvc.perform(get("/api/rents/preview")
            .param("bike-id", String.valueOf(bikeId))
            .param("start-date", startDate.toString())
            .param("end-date", endDate.toString()))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.bikeDto.id", equalTo(bikeId)))
        .andExpect(jsonPath("$.total", equalTo(446.2)));
  }

  @Test
  void shouldHandleBikeNotFound() throws Exception {
    LocalDate startDate = LocalDate.now();
    LocalDate endDate = startDate.plusDays(3);
    when(bikeRentService.getBikeRentPreview(anyInt(), any(), any()))
        .thenThrow(new BikeNotFoundException("Bike with Id=1000 not found"));

    mockMvc.perform(get("/api/rents/preview")
            .param("bike-id", String.valueOf(bikeId))
            .param("start-date", startDate.toString())
            .param("end-date", endDate.toString()))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$", equalTo("Bike with Id=1000 not found")));
  }

  @Test
  void shouldSaveBikeRent() throws Exception {
    LocalDate startDate = LocalDate.now();
    LocalDate endDate = startDate.plusDays(3);

    when(bikeRentService.saveBikeRent(any())).thenReturn(
        createBikeRent(getUser1(), getBike1(), startDate, endDate, 97.0, 58.2, 446.2));


    String request = getBikeRentRequestDtoToString();

    mockMvc.perform(post("/api/rents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(request))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.bikeDto.id", equalTo(bikeId)))
        .andExpect(jsonPath("$.userDto.id", equalTo(userId)))
        .andExpect(jsonPath("$.total", equalTo(446.2)));
  }

  @Test
  void shouldHandleBikeRentException() throws Exception {
    String request = getBikeRentRequestDtoToString();

    when(bikeRentService.saveBikeRent(any())).thenThrow(
        new BikeRentException("There are booked rents for the chosen dates"));

    mockMvc.perform(post("/api/rents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(request))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$", equalTo("There are booked rents for the chosen dates")));
  }

  @Test
  void shouldHandleUserNotFoundException() throws Exception {
    String request = getBikeRentRequestDtoToString();

    when(bikeRentService.saveBikeRent(any())).thenThrow(
        new UserNotFoundException("User with Id=1000 not found"));

    mockMvc.perform(post("/api/rents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(request))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$", equalTo("User with Id=1000 not found")));
  }

  @Test
  void shouldHandleInvalidDateException() throws Exception {
    String request = getBikeRentRequestDtoToString();

    when(bikeRentService.saveBikeRent(any())).thenThrow(
        new InvalidDateException("Invalid dates provided"));

    mockMvc.perform(post("/api/rents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(request))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$", equalTo("Invalid dates provided")));
  }

  private Bike getBike1() {
    return createBike(1, "bike1");
  }

  private User getUser1() {
    return createUser(1, "John", "May");
  }

  private String getBikeRentRequestDtoToString() throws Exception {
    objectMapper.registerModule(new JavaTimeModule());
    return objectMapper.writeValueAsString(
        createBikeRentRequestDto(bikeId, userId, LocalDate.now(),
            LocalDate.now().plusDays(3)));
  }

}
