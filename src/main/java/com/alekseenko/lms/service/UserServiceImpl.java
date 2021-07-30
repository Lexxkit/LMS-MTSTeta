package com.alekseenko.lms.service;

import com.alekseenko.lms.controller.NotFoundException;
import com.alekseenko.lms.dao.RoleRepository;
import com.alekseenko.lms.dao.UserRepository;
import com.alekseenko.lms.domain.Role;
import com.alekseenko.lms.domain.User;
import com.alekseenko.lms.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(RoleRepository roleRepository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDto> findAllUsers() {
        return userRepository.findAll().stream()
                .map(usr -> new UserDto(usr.getId(), usr.getUsername(), "", usr.getRoles()))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getUsersNotAssignedToCourse(Long id) {
        return userRepository.findUsersNotAssignedToCourse(id).stream()
                .map(usr -> new UserDto(usr.getId(), usr.getUsername(), "", usr.getRoles()))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(usr -> new UserDto(usr.getId(), usr.getUsername(), "", usr.getRoles()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public UserDto getUserByUsername(String username) {
        return userRepository.findUserByUsername(username)
                .map(usr -> new UserDto(usr.getId(), usr.getUsername(), "", usr.getRoles()))
                .orElseThrow(NotFoundException::new);
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
            Role studentRole = roleRepository.findRoleByName("ROLE_STUDENT").get();
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
                .map(usr -> new UserDto(usr.getId(), usr.getUsername(), "", usr.getRoles()))
                .orElseThrow(NotFoundException::new);
        return Collections.singletonList(userDto);
    }
}
