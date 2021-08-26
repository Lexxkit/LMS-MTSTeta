package com.alekseenko.lms.service;

import com.alekseenko.lms.domain.User;
import com.alekseenko.lms.domain.VerificationToken;
import com.alekseenko.lms.dto.UserDto;
import com.alekseenko.lms.exception.UserAlreadyRegisteredException;
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

  void saveUser(User user);

  void deleteUser(Long id, String username);

  User registerNewUserAccount(UserDto userDto) throws UserAlreadyRegisteredException;

  void createVerificationToken(User user, String token);

  VerificationToken getVerificationToken(final String VerificationToken);

  Boolean checkIfUserEnabled(String username);

  Boolean checkIfUserEnabled(Long id);

}
