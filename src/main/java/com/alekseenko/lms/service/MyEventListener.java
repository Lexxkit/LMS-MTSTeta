 package com.alekseenko.lms.service;

 import com.alekseenko.lms.dto.UserDto;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.context.event.ContextRefreshedEvent;
 import org.springframework.context.event.EventListener;
 import org.springframework.security.crypto.password.PasswordEncoder;
 import org.springframework.stereotype.Component;

 import java.util.List;
 import java.util.Set;

 @Component
 public class MyEventListener {

     private final RoleService roleService;
     private final UserService userService;
     private final PasswordEncoder passwordEncoder;

     @Autowired
     public MyEventListener(RoleService roleService, UserService userService, PasswordEncoder passwordEncoder) {
         this.roleService = roleService;
         this.userService = userService;
         this.passwordEncoder = passwordEncoder;
     }


 //    Если в БД отсутствуют пользователи,
 //    создаю двух тестовых пользователей, с разными правами (ADMIN, STUDENT)
 //    для проверки функционала задания недели.


     @EventListener
     public void addDefaultUsers(ContextRefreshedEvent e) {
         List<UserDto> users = userService.findAllUsers();
         if (users.isEmpty()) {

             UserDto user1 = new UserDto("Test_admin");
             user1.setPassword("123");
             user1.setRoles(Set.of(roleService.getRoleByName("ROLE_ADMIN")));
             UserDto user2 = new UserDto("Test_student");
             user2.setPassword("123");
             user2.setRoles(Set.of(roleService.getRoleByName("ROLE_STUDENT")));
             userService.saveUser(user1);
             userService.saveUser(user2);
         }
     }
 }
