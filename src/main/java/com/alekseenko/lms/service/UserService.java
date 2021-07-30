package com.alekseenko.lms.service;

import com.alekseenko.lms.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> findAllUsers();

    List<UserDto> getUsersNotAssignedToCourse(Long id);

    UserDto getUserById(Long id);

    UserDto getUserByUsername(String username);

   List<UserDto> assignSingleUserToCourse(String username);

    UserDto getRegistrationTemplate();

    void saveUser(UserDto userDto);
}
