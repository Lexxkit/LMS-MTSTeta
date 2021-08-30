package com.alekseenko.lms.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.alekseenko.lms.service.CourseService;
import com.alekseenko.lms.service.LessonService;
import com.alekseenko.lms.service.ModuleService;
import com.alekseenko.lms.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ModuleController.class)
public class ModuleControllerTest {

  @MockBean
  private ModuleService moduleService;
  @MockBean
  private LessonService lessonService;
  @MockBean
  private CourseService courseService;
  @MockBean
  private UserService userService;

  @Autowired
  private MockMvc mockMvc;

  @Test
  @WithMockUser(roles = {"ADMIN"})
  void testGetModuleTemplatePage() throws Exception {
    mockMvc.perform(get("/module/new")
        .param("course_id", "1"))
        .andExpect(view().name("module-form"));
  }

}
