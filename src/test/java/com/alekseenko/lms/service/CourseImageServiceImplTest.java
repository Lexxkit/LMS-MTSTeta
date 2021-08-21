package com.alekseenko.lms.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.alekseenko.lms.dao.CourseImageRepository;
import com.alekseenko.lms.dao.CourseRepository;
import com.alekseenko.lms.domain.Course;
import com.alekseenko.lms.domain.CourseImage;
import com.alekseenko.lms.domain.User;
import com.alekseenko.lms.exception.NotFoundException;
import java.util.List;
import java.util.Set;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestInstance(Lifecycle.PER_CLASS)
@Transactional
public class CourseImageServiceImplTest {
    @MockBean
    private static Logger logger;
    @Autowired
    private CourseImageRepository courseImageRepository;
    @Autowired
    private CourseImageService courseImageService;
    @Autowired
    private CourseRepository courseRepository;

    @Value("${file.storage.logo.path}")
    private String path;

    private User TEST_USER;

    @BeforeAll
    void setUp() {
        TEST_USER = new User(1L, "Test", "", Set.of());
        var auth = new UsernamePasswordAuthenticationToken(TEST_USER, null);
        SecurityContextHolder.getContext().setAuthentication(auth);
        var course1 = new Course(1L, "Oleg", "Learning test", null);
        var course2 = new Course(2L, "Test", "Programming for beginners", null);
        courseRepository.saveAll(List.of(course1, course2));
        var courseImage = new CourseImage(1L, "png", "Test", courseRepository.getById(1L));
        var courseImage2 = new CourseImage(2L, "jpeg", "Test", courseRepository.getById(2L));
        courseImageRepository.saveAll(List.of(courseImage, courseImage2));
    }

    @BeforeEach
    void setAuth() {
        var auth = new UsernamePasswordAuthenticationToken(TEST_USER, null);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    void shouldReturnContentTypeForCourse() throws Exception {
        final var contentType = courseImageService.getContentTypeByCourse(1L);

        assertThat(contentType).isEqualTo("png");
    }

    @Test
    void shouldThrowNotFoundExceptionForWrongCourse() throws Exception {
        assertThatThrownBy(() -> {
            courseImageService.getContentTypeByCourse(null);
        }).isInstanceOf(NotFoundException.class);
    }

    @Test
    void shouldSaveCourseImage() {
//        courseImageService.saveCourseImage(1L, "png", InputStream.nullInputStream());
//        courseImageService.saveCourseImage(2L, "jpeg", InputStream.nullInputStream());

        final var contentTypeForCourseWithImage = courseImageService.getContentTypeByCourse(1L);
        final var contentTypeForCourseWithoutImage = courseImageService.getContentTypeByCourse(2L);

        assertThat(contentTypeForCourseWithImage).isEqualTo("png");
        assertThat(contentTypeForCourseWithoutImage).isEqualTo("jpeg");
    }
}
