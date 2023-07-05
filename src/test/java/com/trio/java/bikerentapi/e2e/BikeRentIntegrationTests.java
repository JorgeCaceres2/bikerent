package com.trio.java.bikerentapi.e2e;

import static com.trio.java.bikerentapi.util.ObjectsFactory.createBikeRentRequestDto;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.trio.java.bikerentapi.util.ObjectsFactory;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test-application.properties")
class BikeRentIntegrationTests {

  @Autowired
  private MockMvc mockMvc;

  private final ObjectMapper objectMapper = new ObjectMapper();

  private final int bikeId = 1;
  private final int userId = 1;

  @Test
  void shouldReturnBikeRentDetails() throws Exception {
    mockMvc.perform(get("/api/rents/preview")
            .param("bike-id", String.valueOf(bikeId))
            .param("start-date", LocalDate.now().toString())
            .param("end-date", LocalDate.now().plusDays(3).toString()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.bikeDto.id", equalTo(bikeId)))
        .andExpect(jsonPath("$.total", equalTo(446.2)));
  }

  @Test
  void shouldSaveBikeRent() throws Exception {
    objectMapper.registerModule(new JavaTimeModule());
    String request = objectMapper.writeValueAsString(
        createBikeRentRequestDto(bikeId, userId, LocalDate.now().plusDays(4),
            LocalDate.now().plusDays(6)));
    mockMvc.perform(post("/api/rents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(request))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.bikeDto.id", equalTo(bikeId)))
        .andExpect(jsonPath("$.userDto.id", equalTo(userId)))
        .andExpect(jsonPath("$.total", equalTo(334.65)));
  }

  @Test
  void shouldThrowBikeNotFoundException() throws Exception {
    int invalidBikeId = 1000;
    objectMapper.registerModule(new JavaTimeModule());
    String request = objectMapper.writeValueAsString(
        createBikeRentRequestDto(invalidBikeId, userId, LocalDate.now().plusDays(4),
            LocalDate.now().plusDays(6)));
    mockMvc.perform(post("/api/rents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(request))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$", equalTo("Bike with Id=1000 not found")));
  }

  @Test
  void shouldThrowUserNotFoundException() throws Exception {
    int invalidUserId = 1000;
    objectMapper.registerModule(new JavaTimeModule());
    String request = objectMapper.writeValueAsString(
        createBikeRentRequestDto(bikeId, invalidUserId, LocalDate.now().plusDays(4),
            LocalDate.now().plusDays(6)));
    mockMvc.perform(post("/api/rents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(request))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$", equalTo("User with Id=1000 not found")));
  }

  @Test
  void shouldThrowBikeRentException() throws Exception {
    objectMapper.registerModule(new JavaTimeModule());
    String request = objectMapper.writeValueAsString(
        createBikeRentRequestDto(bikeId, userId, LocalDate.now(), LocalDate.now().plusDays(6)));
    mockMvc.perform(post("/api/rents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(request))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$", equalTo("There are booked rents for the chosen dates")));
  }

  @Test
  void shouldThrowInvalidDateException() throws Exception {
    objectMapper.registerModule(new JavaTimeModule());
    String request = objectMapper.writeValueAsString(
        createBikeRentRequestDto(bikeId, userId, LocalDate.now(), LocalDate.now().plusDays(-6)));
    mockMvc.perform(post("/api/rents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(request))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$", equalTo("Invalid dates provided")));
  }
}
