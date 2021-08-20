package com.alekseenko.lms.service;

import static com.alekseenko.lms.RoleConstants.ROLE_ADMIN;
import static com.alekseenko.lms.RoleConstants.ROLE_OWNER;
import static com.alekseenko.lms.RoleConstants.ROLE_STUDENT;
import static com.alekseenko.lms.RoleConstants.ROLE_TUTOR;

import com.alekseenko.lms.dao.RoleRepository;
import com.alekseenko.lms.domain.Role;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MyEventListener {
  // Заполняю таблицу Roles при старте приложения, если она пустая

  private final RoleRepository roleRepository;

  @Autowired
  public MyEventListener(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @EventListener
  public void addDefaultRoles(ContextRefreshedEvent e) {
    List<Role> roles = roleRepository.findAll();
    if (roles.isEmpty()) {
      Role role_owner = new Role(ROLE_OWNER);
      Role role_admin = new Role(ROLE_ADMIN);
      Role role_tutor = new Role(ROLE_TUTOR);
      Role role_student = new Role(ROLE_STUDENT);
      roleRepository.saveAll(List.of(role_owner, role_admin, role_tutor, role_student));
    }
  }
}
