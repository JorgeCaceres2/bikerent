package com.trio.java.bikerentapi.repository.impl.database;

import com.trio.java.bikerentapi.data.User;
import com.trio.java.bikerentapi.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

public class DatabaseUserRepository implements UserRepository {

  @Autowired
  private MySqlUserRepository db;

  @Override
  public Optional<User> getUserById(int id) {
    return db.findById(id);
  }
}
