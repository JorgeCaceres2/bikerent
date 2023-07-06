package com.trio.java.bikerentapi.repository;

import com.trio.java.bikerentapi.data.User;
import java.util.Optional;

public interface UserRepository {

  Optional<User> getUserById(int id);

}
