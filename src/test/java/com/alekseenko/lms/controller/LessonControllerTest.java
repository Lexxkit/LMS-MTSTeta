package com.alekseenko.lms.controller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.alekseenko.lms.dto.LessonDto;
import com.alekseenko.lms.exception.NotFoundException;
import com.alekseenko.lms.service.LessonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(LessonController.class)
public class LessonControllerTest {
    @MockBean
    private LessonService lessonService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles={"ADMIN"})
    void testGetLessonTemplatePage() throws Exception {
        mockMvc.perform(get("/lesson/new")
                .param("module_id", "1"))
                .andExpect(view().name("lesson-form"));
    }

    @Test
    void testLessonPage() throws Exception {
        LessonDto lesson = new LessonDto(1L, "New course", 1L);

        when(lessonService.getLessonById(1L)).thenReturn(lesson);

        mockMvc.perform(get("/lesson/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("lesson-form"));
    }

    @Test
    void testLessonPageNotFound() throws Exception {
        when(lessonService.getLessonById(2L)).thenThrow(NotFoundException.class);
        mockMvc.perform(get("/lesson/{id}", 2L))
                .andExpect(status().isNotFound())
                .andExpect(view().name("not_found"));
    }

    @Test
    void testSubmitValidLessonForm() throws Exception {
        mockMvc.perform(post("/lesson")
                .with(csrf())
                .flashAttr("lessonDto", new LessonDto(1L, "Title", "Text", 1L)))
                .andExpect(model().hasNoErrors())
                .andExpect(view().name(String.format("redirect:/module/%d", 1L)));
    }

    @Test
    void testSubmitInvalidLessonForm() throws Exception {
        mockMvc.perform(post("/lesson")
                .with(csrf())
                .flashAttr("lessonDto", new LessonDto(1L, "", "", 1L)))
                .andExpect(model().attributeHasFieldErrors("lessonDto", "title", "content"))
                .andExpect(view().name("lesson-form"));
    }

    @Test
    void testDeleteLesson() throws Exception {
        mockMvc.perform(delete("/lesson/{id}", 1L)
                .with(csrf())
                .param("courseId", "1"))
                .andExpect(view().name(String.format("redirect:/course/%d", 1L)));
    }
}
