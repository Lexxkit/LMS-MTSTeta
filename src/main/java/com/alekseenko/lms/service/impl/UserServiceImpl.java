package com.alekseenko.lms.service.impl;

import com.alekseenko.lms.constants.RoleConstants;
import com.alekseenko.lms.dao.RoleRepository;
import com.alekseenko.lms.dao.UserRepository;
import com.alekseenko.lms.domain.Role;
import com.alekseenko.lms.dto.UserDto;
import com.alekseenko.lms.exception.NotFoundException;
import com.alekseenko.lms.mapper.UserMapper;
import com.alekseenko.lms.service.UserService;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final RoleRepository roleRepository;
  private final UserMapper userMapper;
  private final UserRepository userRepository;

  @Autowired
  public UserServiceImpl(RoleRepository roleRepository,
      UserMapper userMapper,
      UserRepository userRepository) {
    this.roleRepository = roleRepository;
    this.userMapper = userMapper;
    this.userRepository = userRepository;
  }

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

  @Override
  public void saveUser(UserDto userDto) {
    // New users get ROLE_STUDENT upon self registration
    if (userDto.getRoles() == null) {
      Role studentRole = roleRepository.findRoleByName(RoleConstants.ROLE_STUDENT)
          .orElseThrow(
              () -> new NotFoundException(String.format("Role %s not found", userDto.getRoles())));
      userDto.setRoles(Set.of(studentRole));
    }
    userRepository.save(userMapper.mapToUser(userDto));
  }

  @Override
  public List<UserDto> assignSingleUserToCourse(String username) {
    UserDto userDto = userRepository.findUserByUsername(username)
        .map(userMapper::mapToUserDto)
        .orElseThrow(() -> new NotFoundException(String.format("User %s not found", username)));
    return Collections.singletonList(userDto);
  }
}
