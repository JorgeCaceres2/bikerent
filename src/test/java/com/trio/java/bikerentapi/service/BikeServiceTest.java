package com.trio.java.bikerentapi.service;

import static org.mockito.Mockito.when;

import com.trio.java.bikerentapi.repository.BikeRepository;
import com.trio.java.bikerentapi.util.ObjectsFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:test-application.properties")
class BikeServiceTest {

    @MockBean
    private BikeRepository bikeRepository;

    @Autowired
    private BikeService bikeService;

    @Test
    void shouldReturnEmptyListIfNoBikesAvailable() {
        when(bikeRepository.getAllBikes()).thenReturn(new ArrayList<>());

        var result = bikeService.getAllBikes();
        assert (result.size() == 0);
    }

    @Test
    void shouldReturnBikes() {
        when(bikeRepository.getAllBikes()).thenReturn(Arrays.asList(
                ObjectsFactory.createBike(1, "Bike1"),
                ObjectsFactory.createBike(2, "Bike2")
        ));

        var result = bikeService.getAllBikes();
        assert (result.size() == 2);
        assert (result.get(0).getId() == 1);
        assert (result.get(0).getName().equals("Bike1"));
        assert (result.get(1).getId() == 2);
        assert (result.get(1).getName().equals("Bike2"));
    }

    @Test
    void shouldReturnBikeDetails() {
        int id = 308;

        when(bikeRepository.getBikeDetails(id))
                .thenReturn(Optional.of(ObjectsFactory.createBike(id, "Some bike")));

        var result = bikeService.getBikeDetails(id);
        assert (result.isPresent());
        assert (result.get().getId() == id);
        assert (result.get().getName().equals("Some bike"));
    }

    @Test
    void shouldReturnEmptyResponseIfBikeDetailsNotPresent() {
        int id = 404;

        when(bikeRepository.getBikeDetails(id)).thenReturn(Optional.empty());

        var result = bikeService.getBikeDetails(id);
        assert (result.isEmpty());
    }


}
