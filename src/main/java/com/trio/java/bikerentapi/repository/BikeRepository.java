package com.trio.java.bikerentapi.repository;

import com.trio.java.bikerentapi.data.Bike;
import java.util.List;
import java.util.Optional;

public interface BikeRepository {
    List<Bike> getAllBikes();

    Optional<Bike> getBikeDetails(int id);
}
