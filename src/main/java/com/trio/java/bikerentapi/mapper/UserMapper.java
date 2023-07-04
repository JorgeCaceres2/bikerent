package com.trio.java.bikerentapi.mapper;

import com.trio.java.bikerentapi.data.User;
import com.trio.java.bikerentapi.dto.UserDto;

public class UserMapper {

  public UserDto fromUser(User user) {
    return UserDto.builder()
        .withId(user.getId())
        .withFirstName(user.getFirstName())
        .withLastName(user.getLastName())
        .build();
  }
}
