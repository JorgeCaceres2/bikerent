package com.trio.java.bikerentapi.repository.impl.database;

import com.trio.java.bikerentapi.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MySqlUserRepository extends JpaRepository<User, Integer> {

}
