package com.alekseenko.lms.service;

import com.alekseenko.lms.RoleConstants;
import com.alekseenko.lms.dao.RoleRepository;
import com.alekseenko.lms.dao.UserRepository;
import com.alekseenko.lms.domain.Role;
import com.alekseenko.lms.domain.User;
import com.alekseenko.lms.dto.UserDto;
import com.alekseenko.lms.exception.NotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final RoleRepository roleRepository;
  private final UserMapper userMapper;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserServiceImpl(RoleRepository roleRepository,
      UserMapper userMapper,
      UserRepository userRepository,
      PasswordEncoder passwordEncoder) {
    this.roleRepository = roleRepository;
    this.userMapper = userMapper;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
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
    UserDto userDto = new UserDto();
    return userDto;
  }

  @Override
  public void saveUser(UserDto userDto) {
    // New users get ROLE_STUDENT upon registration
    if (userDto.getRoles() == null) {
      Role studentRole = roleRepository.findRoleByName(RoleConstants.ROLE_STUDENT)
          .orElseThrow(
              () -> new NotFoundException(String.format("Role %s not found", userDto.getRoles())));
      userDto.setRoles(Set.of(studentRole));
    }
    userRepository.save(new User(userDto.getId(),
        userDto.getUsername(),
        passwordEncoder.encode(userDto.getPassword()),
        userDto.getRoles()
    ));
  }

  @Override
  public List<UserDto> assignSingleUserToCourse(String username) {
    UserDto userDto = userRepository.findUserByUsername(username)
        .map(userMapper::mapToUserDto)
        .orElseThrow(() -> new NotFoundException(String.format("User %s not found", username)));
    return Collections.singletonList(userDto);
  }
}
