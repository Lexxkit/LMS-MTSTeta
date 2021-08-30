package com.alekseenko.lms.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.alekseenko.lms.domain.CourseImage;
import com.alekseenko.lms.dto.CourseDto;
import com.alekseenko.lms.dto.UserDto;
import com.alekseenko.lms.service.AvatarImageService;
import com.alekseenko.lms.service.CourseService;
import com.alekseenko.lms.service.RoleService;
import com.alekseenko.lms.service.UserService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserProfileController.class)
public class UserProfileControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private AvatarImageService avatarImageService;
  @MockBean
  private CourseService courseService;
  @MockBean
  private RoleService roleService;
  @MockBean
  private UserService userService;
  @MockBean
  private Logger logger;

  @Test
  void testGetUserProfileForAnonymousUser() throws Exception {
    CourseDto testCourse = new CourseDto(null, "Title", "Author", (CourseImage) null);

    when(courseService.getCoursesForUser("Test_user")).thenReturn(List.of(testCourse));
    when(avatarImageService.getAvatarImageByUser("")).thenReturn(Optional.empty());

    mockMvc.perform(get("/profile"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/login"));
  }

  @Test
  @WithMockUser(username = "admin")
  void testGetUserProfileForAuthUser() throws Exception {
    CourseDto testCourse = new CourseDto(null, "Title", "Author", (CourseImage) null);

    when(courseService.getCoursesForUser("Test_user")).thenReturn(List.of(testCourse));
    when(avatarImageService.getAvatarImageByUser("Test_user")).thenReturn(Optional.empty());
    when(userService.getUserByUsername("admin")).thenReturn(new UserDto("admin"));

    mockMvc.perform(get("/profile"))
        .andExpect(status().isOk())
        .andExpect(view().name("user-profile"));
  }
}
