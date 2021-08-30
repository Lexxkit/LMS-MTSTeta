package com.alekseenko.lms.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.alekseenko.lms.dto.UserDto;
import com.alekseenko.lms.service.CourseService;
import com.alekseenko.lms.service.RoleService;
import com.alekseenko.lms.service.UserService;
import com.alekseenko.lms.validator.TitleCaseValidator;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AdminUserController.class)
public class AdminUserControllerTest {

  @MockBean
  private UserService userService;
  @MockBean
  private RoleService roleService;
  @MockBean
  private CourseService courseService;
  @MockBean
  private TitleCaseValidator titleCaseValidator;
  @Autowired
  private MockMvc mockMvc;

  @Test
  @WithMockUser(roles = {"ADMIN"})
  void testUserTable() throws Exception {
    UserDto user = new UserDto("Test user");

    when(userService.findAllUsers()).thenReturn(List.of(user));

    mockMvc.perform(get("/admin/user"))
        .andExpect(status().isOk())
        .andExpect(view().name("user-table"));
  }

  @Test
  @WithMockUser(roles = {"ADMIN"})
  void testCurrentUserForm() throws Exception {
    UserDto user = new UserDto(1L, "Test user", null, new HashSet<>());

    when(userService.getUserById(1L)).thenReturn(user);

    mockMvc.perform(get("/admin/user/{id}", 1L))
        .andExpect(status().isOk())
        .andExpect(view().name("user-edit"));
  }

//    @Test
//    @WithMockUser(roles = {"ADMIN"})
//    void testSubmitValidUserForm() throws Exception {
//        var userDto = new UserDto("Test");
//        userDto.setPassword("123");
//        userDto.setEmail("test@test.com");
//        userDto.setRoles(new HashSet<>());
//
//        mockMvc.perform(post("/admin/user")
//                .with(csrf())
//                .flashAttr("user", userDto))
//                .andExpect(model().hasNoErrors())
//                .andExpect(redirectedUrl("/admin/user"));
//    }
//
//    @Test
//    @WithMockUser(roles = {"ADMIN"})
//    void testSubmitInvalidUserForm() throws Exception {
//        final var testUser = new UserDto(1L, "Test", "12345678", new HashSet<>());
//        testUser.setEmail("t@t.com");
//        mockMvc.perform(post("/admin/user")
//                .with(csrf())
//                .flashAttr("user", testUser))
//                .andExpect(model().attributeHasFieldErrors("user"))
//                .andExpect(view().name("user-edit"));
//    }
}
