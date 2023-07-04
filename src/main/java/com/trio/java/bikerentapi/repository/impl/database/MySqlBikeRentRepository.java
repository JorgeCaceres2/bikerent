package com.trio.java.bikerentapi.repository.impl.database;

import com.trio.java.bikerentapi.data.Bike;
import com.trio.java.bikerentapi.data.BikeRent;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MySqlBikeRentRepository extends JpaRepository<BikeRent, Integer> {

//  @Query("SELECT br FROM BikeRent br WHERE br.bike = :bike "
//      + "AND br.startDate BETWEEN :startDate AND :endDate "
//      + "AND br.endDate BETWEEN :startDate AND :endDate")

  @Query("SELECT br FROM BikeRent br WHERE br.bike = :bike "
      + "AND (br.startDate BETWEEN :startDate AND :endDate "
      + "OR br.endDate BETWEEN :startDate AND :endDate "
      + "OR :startDate BETWEEN br.startDate AND br.endDate "
      + "OR :endDate BETWEEN br.startDate AND br.endDate)")
  List<BikeRent> getBookedBikeRents(Bike bike, LocalDate startDate, LocalDate endDate);

}
