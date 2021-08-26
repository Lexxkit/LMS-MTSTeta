package com.alekseenko.lms.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.alekseenko.lms.dto.UserDto;
import com.alekseenko.lms.service.RoleService;
import com.alekseenko.lms.service.UserService;
import java.util.HashSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
public class UserControllerTest {

  @MockBean
  private UserService userService;
  @MockBean
  private RoleService roleService;
  @Autowired
  private MockMvc mockMvc;

//    @Test
//    void testUserRegistration() throws Exception {
//        when(userService.getRegistrationTemplate()).thenReturn(new UserDto());
//
//        mockMvc.perform(get("/user/registration"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("user-create"));
//    }

  @Test
  void testCurrentUserForm() throws Exception {
    UserDto user = new UserDto(1L, "Test user", null, new HashSet<>());

    when(userService.getUserById(1L)).thenReturn(user);

    mockMvc.perform(get("/user/{id}", 1L))
        .andExpect(view().name("user-create"));
  }

  @Test
  void testLoginUnauthenticatedUser() throws Exception {
    mockMvc.perform(get("/user"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/login"));
  }

//    @Test
//    void testValidRegisterUser() throws Exception {
//        var userDto = new UserDto("Test");
//        userDto.setPassword("123");
//        userDto.setEmail("test@test.com");
//        userDto.setRoles(new HashSet<>());
//
//        mockMvc.perform(post("/user")
//                .with(csrf())
//                .flashAttr("user", userDto))
//                .andExpect(model().hasNoErrors())
//                .andExpect(redirectedUrl("/login"));
//    }
//
//    @Test
//    void testInvalidRegisterUser() throws Exception {
//        mockMvc.perform(post("/user")
//                .with(csrf())
//                .flashAttr("user", new UserDto(1L, "", "", new HashSet<>())))
//                .andExpect(model().attributeHasFieldErrors("user"))
//                .andExpect(view().name("user-create"));
//    }
}
