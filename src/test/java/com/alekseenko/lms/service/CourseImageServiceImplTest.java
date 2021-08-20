package com.alekseenko.lms.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.alekseenko.lms.dao.CourseImageRepository;
import com.alekseenko.lms.dao.CourseRepository;
import com.alekseenko.lms.domain.Course;
import com.alekseenko.lms.domain.CourseImage;
import com.alekseenko.lms.exception.NotFoundException;
import java.io.InputStream;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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
    @MockBean
    private MyEventListener myEventListener;

    @Value("${file.storage.path}")
    private String path;

    @BeforeAll
    void setUp() {
        Course course1 = new Course(1L, "Oleg", "Learning test", null);
        Course course2 = new Course(2L, "Test", "Programming for beginners", null);
        courseRepository.saveAll(List.of(course1, course2));
        CourseImage courseImage = new CourseImage(1L, "jpeg", "Test", courseRepository.getById(1L));
        courseImageRepository.save(courseImage);
    }

    @Test
    void shouldReturnContentTypeForCourse() throws Exception {
        final var contentType = courseImageService.getContentTypeByCourse(1L);

        assertThat(contentType).isEqualTo("jpeg");
    }

    @Test
    void shouldThrowNotFoundExceptionForWrongCourse() throws Exception {
        assertThatThrownBy(() -> {
            courseImageService.getContentTypeByCourse(null);
        }).isInstanceOf(NotFoundException.class);
    }

    @Test
    void shouldSaveCourseImage() {
        courseImageService.saveCourseImage(1L, "png", InputStream.nullInputStream());
        courseImageService.saveCourseImage(2L, "jpeg", InputStream.nullInputStream());

        final var contentTypeForCourseWithImage = courseImageService.getContentTypeByCourse(1L);
        final var contentTypeForCourseWithoutImage = courseImageService.getContentTypeByCourse(2L);

        assertThat(contentTypeForCourseWithImage).isEqualTo("png");
        assertThat(contentTypeForCourseWithoutImage).isEqualTo("jpeg");
    }
}
