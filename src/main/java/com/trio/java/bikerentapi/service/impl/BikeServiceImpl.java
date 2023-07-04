package com.trio.java.bikerentapi.service.impl;

import com.trio.java.bikerentapi.data.Bike;
import com.trio.java.bikerentapi.repository.BikeRepository;
import com.trio.java.bikerentapi.service.BikeService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BikeServiceImpl implements BikeService {

    @Autowired
    BikeRepository bikeRepository;

    public List<Bike> getAllBikes() {
        return bikeRepository.getAllBikes();
    }

    public Optional<Bike> getBikeDetails(int id) {
        return bikeRepository.getBikeDetails(id);
    }

}
