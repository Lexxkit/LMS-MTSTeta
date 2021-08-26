package com.alekseenko.lms.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.alekseenko.lms.dao.CourseRepository;
import com.alekseenko.lms.domain.Course;
import com.alekseenko.lms.domain.User;
import com.alekseenko.lms.dto.CourseDto;
import com.alekseenko.lms.event.RegistrationListener;
import com.alekseenko.lms.mapper.CourseMapper;
import java.util.List;
import java.util.Set;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class CourseMapperTest {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    CourseMapper courseMapper;
    @MockBean
    RegistrationListener registrationListener;

    private User TEST_USER;

    @BeforeAll
    void setUp() {
        TEST_USER = new User(1L, "Test", "", Set.of());
        var auth = new UsernamePasswordAuthenticationToken(TEST_USER, null);
        SecurityContextHolder.getContext().setAuthentication(auth);
        Course testCourseWithoutUser = new Course(1L, "Author", "Title", null);
        Course testCourseWithUser = new Course(2L, "Author", "Title", null);
        courseRepository.saveAll(List.of(testCourseWithoutUser, testCourseWithUser));
    }

    @BeforeEach
    void setAuth() {
        var auth = new UsernamePasswordAuthenticationToken(TEST_USER, null);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    void shouldReturnCourseDtoObject() {
        final var testCourseDtoWithoutUser = courseMapper.mapToCourseDtoWithoutUser(
            courseRepository.getById(1L));
        final var testCourseDtoWithoutImage = courseMapper.mapToCourseDtoWithoutUser(
            courseRepository.getById(1L));

        assertThat(testCourseDtoWithoutUser).isInstanceOf(CourseDto.class);
        assertThat(testCourseDtoWithoutUser.getUsers()).isNull();

        assertThat(testCourseDtoWithoutImage).isInstanceOf(CourseDto.class);
        assertThat(testCourseDtoWithoutUser.getCourseImage()).isNull();
    }
}
