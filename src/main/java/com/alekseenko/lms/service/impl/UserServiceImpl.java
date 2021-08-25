package com.alekseenko.lms.service.impl;

import com.alekseenko.lms.constants.RoleConstants;
import com.alekseenko.lms.dao.RoleRepository;
import com.alekseenko.lms.dao.TokenRepository;
import com.alekseenko.lms.dao.UserRepository;
import com.alekseenko.lms.domain.Role;
import com.alekseenko.lms.domain.User;
import com.alekseenko.lms.domain.VerificationToken;
import com.alekseenko.lms.dto.UserDto;
import com.alekseenko.lms.exception.AccessDeniedException;
import com.alekseenko.lms.exception.NotFoundException;
import com.alekseenko.lms.exception.UserAlreadyRegisteredException;
import com.alekseenko.lms.mapper.UserMapper;
import com.alekseenko.lms.service.UserService;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

  private final RoleRepository roleRepository;
  private final UserMapper userMapper;
  private final UserRepository userRepository;
  private final TokenRepository tokenRepository;

  @Override
  public List<UserDto> getUsers(Long id, HttpServletRequest request) {
    if (request.isUserInRole(RoleConstants.ROLE_ADMIN)) {
      return getUsersNotAssignedToCourse(id);
    } else {
      return assignSingleUserToCourse(request.getRemoteUser());
    }
  }

  @Override
  public List<UserDto> findAllUsers() {
    return userRepository.findAll().stream()
        .map(userMapper::mapToUserDto)
        .collect(Collectors.toList());
  }

  @Override
  public List<UserDto> getUsersNotAssignedToCourse(Long id) {
    return userRepository.findUsersNotAssignedToCourse(id).stream()
        .map(userMapper::mapToUserDto)
        .collect(Collectors.toList());
  }

  @Override
  public UserDto getUserById(Long id) {
    return userRepository.findById(id)
        .map(userMapper::mapToUserDto)
        .orElseThrow(() -> new NotFoundException(String.format("User with id#%d not found", id)));
  }

  @Override
  public UserDto getUserByUsername(String username) {
    return userRepository.findUserByUsername(username)
        .map(userMapper::mapToUserDto)
        .orElseThrow(() -> new NotFoundException(String.format("User %s not found", username)));
  }

  @Override
  public UserDto getRegistrationTemplate() {
    return new UserDto();
  }

  private boolean emailExist(String email) {
    return userRepository.findUserByEmail(email) != null;
  }

  private boolean loginExists(String name) {
    return userRepository.findUserByUsername(name).isPresent();
  }

  @Override
  public User registerNewUserAccount(UserDto userDto) throws UserAlreadyRegisteredException {
    if (emailExist(userDto.getEmail())) {
      throw new UserAlreadyRegisteredException("Пользователь с этим email уже зарегистрирован",
          "email");
    }
    if (loginExists(userDto.getUsername())) {
      throw new UserAlreadyRegisteredException("Пользователь с этим username уже зарегистрирован",
          "username");
    }
    // New users get ROLE_STUDENT upon self registration
    if (userDto.getRoles() == null) {
      Role studentRole = roleRepository.findRoleByName(RoleConstants.ROLE_STUDENT)
          .orElseThrow(
              () -> new NotFoundException(String.format("Role %s not found", userDto.getRoles())));
      userDto.setRoles(Set.of(studentRole));
    }
    var user = userMapper.mapToUser(userDto);
    userRepository.save(user);
    return (user);
  }

  @Override
  public void createVerificationToken(User user, String token) {
    VerificationToken myToken = new VerificationToken(token, user);
    tokenRepository.save(myToken);
  }

  @Override
  public VerificationToken getVerificationToken(final String VerificationToken) {
    return tokenRepository.findByToken(VerificationToken);
  }

  @Override
  public void saveUser(UserDto userDto) {
    userRepository.save(userMapper.mapToUser(userDto));
  }

  @Override
  public void saveUser(User user) {
    userRepository.save(user);
  }

  @Override
  public List<UserDto> assignSingleUserToCourse(String username) {
    UserDto userDto = userRepository.findUserByUsername(username)
        .map(userMapper::mapToUserDto)
        .orElseThrow(() -> new NotFoundException(String.format("User %s not found", username)));
    return Collections.singletonList(userDto);
  }

  @Override
  public void deleteUser(Long id, String username) {
    if (userRepository.getById(id).getUsername().equals(username)) {
      throw new AccessDeniedException("You can't delete yourself");
    } else {
      userRepository.deleteById(id);
    }
  }
}
