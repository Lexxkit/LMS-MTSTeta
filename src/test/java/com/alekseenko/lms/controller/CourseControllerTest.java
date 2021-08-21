package com.alekseenko.lms.controller;

import com.alekseenko.lms.domain.CourseImage;
import com.alekseenko.lms.dto.CourseDto;
import com.alekseenko.lms.dto.LessonDto;
import com.alekseenko.lms.dto.UserDto;
import com.alekseenko.lms.service.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourseController.class)
public class CourseControllerTest {
    @MockBean
    private CourseService courseService;
    @MockBean
    private CourseImageService courseImageService;
    @MockBean
    private LessonService lessonService;
    @MockBean
    private UserService userService;
    @MockBean
    private StatisticsCounter statisticsCounter;
    @Autowired
    private MockMvc mockMvc;

//    @Test
//    void testIndexPage() throws Exception{
//        CourseDto course = new CourseDto(1L, "Test user", "New course", (CourseImage) null);
//        List<CourseDto> allCourses = Arrays.asList(course);
//
//        when(courseService.getAllCourses()).thenReturn(allCourses);
//
//        mockMvc.perform(get("/course"))
//                .andExpect(view().name("index"));
//    }

//    @Test
//    void testIndexPageWithPrefix() throws Exception {
//        CourseDto course = new CourseDto(1L, "Test user", "New course", (CourseImage) null );
//        List<CourseDto> allCourses = Arrays.asList(course);
//
//        when(courseService.getCoursesByTitleWithPrefix("New" + "%")).thenReturn(allCourses);
//
//        mockMvc.perform(get("/course"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("index"));
//    }

    @Test
    void testCoursePage() throws Exception {
        CourseDto course = new CourseDto(1L, "Test user", "New course", (CourseImage) null);
        LessonDto lesson = new LessonDto(1L, "New course", 1L);

        when(courseService.getCourseById(1L)).thenReturn(course);
        when(lessonService.getAllForLessonIdWithoutText(1L)).thenReturn(List.of(lesson));

        mockMvc.perform(get("/course/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("course-form"));
    }

    @Test
    void testGetCourseTemplatePage() throws Exception {
        when(courseService.getCourseTemplate()).thenReturn(new CourseDto());

        mockMvc.perform(get("/course/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("course-form"));
    }

    @Test
    void testSubmitValidCourseForm() throws Exception {
       mockMvc.perform(post("/course")
               .with(csrf())
               .flashAttr("course", new CourseDto(1L, "Author", "Title", (CourseImage) null)))
               .andExpect(model().hasNoErrors())
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/course"));
    }

    @Test
    void testSubmitInvalidCourseForm() throws Exception {
        mockMvc.perform(post("/course")
                .with(csrf())
                .flashAttr("course", new CourseDto(1L, "", "", (CourseImage) null)))
                .andExpect(model().attributeHasFieldErrors("course", "author", "title"))
                .andExpect(view().name("course-form"));
    }

    @Test
    void testDeleteCourse() throws Exception {
        doNothing().when(courseService).deleteCourse(1L);
        mockMvc.perform(delete("/course/{id}", 1L)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/course"));
    }

    @Test
    @WithMockUser(roles={"ADMIN"})
    void testAssignCourseToUser() throws Exception {
        UserDto user = new UserDto(1L, "Test", "", new HashSet<>());
        when(userService.getUsersNotAssignedToCourse(1L)).thenReturn(List.of(user));

        mockMvc.perform(get("/course/{id}/assign", 1L))
                .andExpect(view().name("course-assign"));
    }
}
