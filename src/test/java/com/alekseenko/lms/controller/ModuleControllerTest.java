package com.alekseenko.lms.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.alekseenko.lms.domain.CourseImage;
import com.alekseenko.lms.domain.Lesson;
import com.alekseenko.lms.dto.CourseDto;
import com.alekseenko.lms.dto.LessonDto;
import com.alekseenko.lms.dto.ModuleDto;
import com.alekseenko.lms.service.CourseService;
import com.alekseenko.lms.service.LessonService;
import com.alekseenko.lms.service.ModuleService;
import java.util.List;
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

  @Autowired
  private MockMvc mockMvc;

  @Test
  @WithMockUser(roles={"ADMIN"})
  void testGetModuleTemplatePage() throws Exception {
    mockMvc.perform(get("/module/new")
        .param("course_id", "1"))
        .andExpect(view().name("module-form"));
  }

}
