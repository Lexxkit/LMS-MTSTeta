package com.alekseenko.lms.service;

import com.alekseenko.lms.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();

    List<User> getUsersNotAssignedToCourse(Long id);

    Optional<User> getUserById(Long id);

    void saveUser(User user);
}
