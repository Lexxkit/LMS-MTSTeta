package com.alekseenko.lms.service;

import static com.alekseenko.lms.RoleConstants.ROLE_ADMIN;
import static com.alekseenko.lms.RoleConstants.ROLE_OWNER;
import static com.alekseenko.lms.RoleConstants.ROLE_STUDENT;
import static com.alekseenko.lms.RoleConstants.ROLE_TUTOR;

import com.alekseenko.lms.RoleConstants;
import com.alekseenko.lms.dao.RoleRepository;
import com.alekseenko.lms.dao.UserRepository;
import com.alekseenko.lms.domain.Role;
import com.alekseenko.lms.domain.User;
import com.alekseenko.lms.dto.UserDto;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyEventListener {

  private final RoleRepository roleRepository;
  private final UserService userService;

  private final UserRepository userRepository;

  @Autowired
  public MyEventListener(RoleRepository roleRepository, UserService userService, UserRepository userRepository,
      PasswordEncoder passwordEncoder) {
    this.roleRepository = roleRepository;
    this.userService = userService;
    this.userRepository = userRepository;
  }

  //    Если в БД отсутствуют пользователи и роли
  //    создаю роли и двух тестовых пользователей, с разными правами (ADMIN, STUDENT)
  //    для проверки функционала задания недели.


  @EventListener
  public void addDefaultUsers(ContextRefreshedEvent e) {
    List<Role> roles = roleRepository.findAll();
    if (roles.isEmpty()) {
      Role role_owner = new Role(ROLE_OWNER);
      Role role_admin = new Role(ROLE_ADMIN);
      Role role_tutor = new Role(ROLE_TUTOR);
      Role role_student = new Role(ROLE_STUDENT);
      roleRepository.saveAll(List.of(role_admin, role_student));
    }

    List<UserDto> users = userService.findAllUsers();
    if (users.isEmpty()) {

      UserDto user1 = new UserDto("Test_admin");
      user1.setPassword("123");
      user1.setRoles(Set.of(roleRepository.findRoleByName(ROLE_ADMIN).get()));
      UserDto user2 = new UserDto("Test_student");
      user2.setPassword("123");
      user2.setRoles(Set.of(roleRepository.findRoleByName(RoleConstants.ROLE_STUDENT).get()));
      userService.saveUser(user1);
      userService.saveUser(user2);

      // TODO: 18.08.2021  проверка установки связи createdByUser & updatedByUser
      User user3 = new User("Test_DB");
      user3.setCreatedByUser(user3);
      user3.setUpdatedByUser(userRepository.findUserByUsername("Test_admin").get());
      userRepository.save(user3);
    }
  }
}
