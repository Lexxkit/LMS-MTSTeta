package com.alekseenko.lms.controller;

import com.alekseenko.lms.dto.UserDto;
import com.alekseenko.lms.service.RoleService;
import com.alekseenko.lms.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminUserController.class)
public class AdminUserControllerTest {
    @MockBean
    private UserService userService;
    @MockBean
    private RoleService roleService;
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

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void testSubmitValidUserForm() throws Exception {
        mockMvc.perform(post("/admin/user")
                .with(csrf())
                .flashAttr("user", new UserDto(1L, "Title", "123", new HashSet<>())))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/admin/user"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void testSubmitInvalidUserForm() throws Exception {
        mockMvc.perform(post("/admin/user")
                .with(csrf())
                .flashAttr("user", new UserDto(1L, "", "", new HashSet<>())))
                .andExpect(model().attributeHasFieldErrors("user"))
                .andExpect(view().name("user-edit"));
    }
}
