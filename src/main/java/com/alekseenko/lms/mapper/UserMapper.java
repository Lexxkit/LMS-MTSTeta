package com.alekseenko.lms.mapper;

import com.alekseenko.lms.domain.User;
import com.alekseenko.lms.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserMapper(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  public UserDto mapToUserDto(User user) {
    UserDto userDto = new UserDto();

    userDto.setId(user.getId());
    userDto.setFirstName(user.getFirstName());
    userDto.setLastName(user.getLastName());
    userDto.setUsername(user.getUsername());
    userDto.setPassword("");
    userDto.setEmail(user.getEmail());
    userDto.setPhone(user.getPhone());
    userDto.setAchievements(user.getAchievements());
    userDto.setSocialNetworkLink(user.getSocialNetworkLink());
    userDto.setRoles(user.getRoles());

    return userDto;
  }

  public User mapToUser(UserDto userDto) {
    User user = new User();

    user.setId(userDto.getId());
    user.setFirstName(userDto.getFirstName());
    user.setLastName(userDto.getLastName());
    user.setUsername(userDto.getUsername());
    user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    user.setEmail(userDto.getEmail());
    user.setPhone(userDto.getPhone());
    user.setAchievements(userDto.getAchievements());
    user.setSocialNetworkLink(userDto.getSocialNetworkLink());
    user.setRoles(userDto.getRoles());

    return user;
  }
}
