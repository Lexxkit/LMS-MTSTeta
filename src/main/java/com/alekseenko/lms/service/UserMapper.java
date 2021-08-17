package com.alekseenko.lms.service;

import com.alekseenko.lms.domain.User;
import com.alekseenko.lms.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

  public UserDto mapToUserDto(User user) {
    UserDto userDto = new UserDto();

    userDto.setId(user.getId());
    userDto.setUsername(user.getUsername());
    userDto.setPassword("");
    userDto.setRoles(user.getRoles());

    return userDto;
  }
}
