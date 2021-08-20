package com.alekseenko.lms.service;

import com.alekseenko.lms.dto.UserDto;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public interface UserService {

  List<UserDto> getUsers(Long id, HttpServletRequest request);

  List<UserDto> findAllUsers();

  List<UserDto> getUsersNotAssignedToCourse(Long id);

  UserDto getUserById(Long id);

  UserDto getUserByUsername(String username);

  List<UserDto> assignSingleUserToCourse(String username);

  UserDto getRegistrationTemplate();

  void saveUser(UserDto userDto);
}
