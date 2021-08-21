package com.alekseenko.lms.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.alekseenko.lms.dao.CourseRepository;
import com.alekseenko.lms.domain.Course;
import com.alekseenko.lms.dto.CourseDto;
import com.alekseenko.lms.mapper.CourseMapper;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class CourseMapperTest {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    CourseMapper courseMapper;

    @BeforeAll
    void setUp() {
        Course testCourseWithoutUser = new Course(1L,"Author", "Title", null);
        Course testCourseWithUser = new Course(2L,"Author", "Title", null);
        courseRepository.saveAll(List.of(testCourseWithoutUser, testCourseWithUser));
    }

    @Test
    void shouldReturnCourseDtoObject() {
        final var testCourseDtoWithoutUser = courseMapper.mapToCourseDtoWithoutUser(courseRepository.getById(1L));
        final var testCourseDtoWithoutImage = courseMapper.mapToCourseDtoWithoutUser(courseRepository.getById(1L));

        assertThat(testCourseDtoWithoutUser).isInstanceOf(CourseDto.class);
        assertThat(testCourseDtoWithoutUser.getUsers()).isNull();

        assertThat(testCourseDtoWithoutImage).isInstanceOf(CourseDto.class);
        assertThat(testCourseDtoWithoutUser.getCourseImage()).isNull();
    }
}
