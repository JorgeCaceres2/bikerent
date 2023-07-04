package com.trio.java.bikerentapi.repository.impl.database;

import com.trio.java.bikerentapi.data.Bike;
import com.trio.java.bikerentapi.data.BikeRent;
import com.trio.java.bikerentapi.repository.BikeRentRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class DatabaseBikeRentRepository implements BikeRentRepository {

  @Autowired
  private MySqlBikeRentRepository db;

  @Override
  public List<BikeRent> getBookedBikeRentsByBike(Bike bike, LocalDate startDate,
      LocalDate endDate) {
    return db.getBookedBikeRents(bike, startDate, endDate);
  }

  @Override
  public BikeRent saveBikeRent(BikeRent bikeRent) {
    return db.save(bikeRent);
  }

  //TODO: is this ok? Create a new repository for each entity?
}
