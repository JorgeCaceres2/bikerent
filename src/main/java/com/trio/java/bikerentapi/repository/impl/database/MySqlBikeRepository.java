package com.trio.java.bikerentapi.repository.impl.database;

import com.trio.java.bikerentapi.data.Bike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MySqlBikeRepository extends JpaRepository<Bike, Integer> {

}
