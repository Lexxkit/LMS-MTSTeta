package com.alekseenko.lms.service;

import com.alekseenko.lms.controller.AccessDeniedException;
import com.alekseenko.lms.controller.NotFoundException;
import com.alekseenko.lms.dao.CourseRepository;
import com.alekseenko.lms.dao.UserRepository;
import com.alekseenko.lms.domain.Course;
import com.alekseenko.lms.domain.CourseImage;
import com.alekseenko.lms.domain.User;
import com.alekseenko.lms.dto.CourseDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class CourseServiceImplTest {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseService courseService;
    @MockBean
    private MyEventListener myEventListener;


    @BeforeAll
    void setUp() {
        Course course1 = new Course(1L, "Oleg", "Learning test", null);
        Course course2 = new Course(2L, "Test", "Programming for beginners", null);
        courseRepository.saveAll(List.of(course1, course2));
    }

    @Test
    void shouldReturnCourseDto() {
        final var courseDto = courseService.getCourseTemplate();
        assertThat(courseDto).isNotNull();
        assertThat(courseDto).isInstanceOf(CourseDto.class);
    }

    @Test
    void shouldFindAllCourses() {
        final var courses = courseService.getAllCourses();
        assertThat(courses.size()).isEqualTo(2);
    }

    @Test
    void shouldFindCourseById() throws Exception {
        final var course = courseService.getCourseById(1L);

        assertThat(course.getAuthor()).isEqualTo("Oleg");
        assertThat(course.getTitle()).isEqualTo("Learning test");
        assertThatThrownBy(() -> {
            courseService.getCourseById(999L);
        }).isInstanceOf(NotFoundException.class);
    }

    @Test
    void shouldFindCoursesForUsername() {
        userRepository.save(new User("Test_user"));
        final var testUser = userRepository.findUserByUsername("Test_user").get();
        courseService.setUserCourseConnection(testUser.getId(), courseService.getCourseById(1L).getId());

        final var coursesForUser = courseService.getCoursesForUser("Test_user");

        assertThat(coursesForUser.size()).isEqualTo(1);
    }

    @Test
    void shouldSaveOneCourse() {
        final var courseToSave = new CourseDto(null, "Test2", "Work with DB", (CourseImage) null);
        courseService.saveCourse(courseToSave);
        final var courses = courseService.getAllCourses();

        assertThat(courses.size()).isEqualTo(3);
    }

    @Test
    void shouldDeleteOneCourse() {
        var course = courseRepository.findById(1L);
        assertThat(course).isPresent();

        courseService.deleteCourse(1L);

        course = courseRepository.findById(1L);
        assertThat(course).isEmpty();

        final var courses = courseService.getAllCourses();
        assertThat(courses.size()).isEqualTo(1);
    }

    @Test
    void shouldFindCourseByTitleWithPrefix() {
        var course = courseService.getCoursesByTitleWithPrefix("Prog" + "%");
        assertThat(course.size()).isEqualTo(1);
    }

    @Test
    void shouldSetUserToCourseConnection() throws Exception {
        userRepository.save(new User("Test_user"));
        final var testUser = userRepository.findUserByUsername("Test_user").get();
        courseService.setUserCourseConnection(testUser.getId(), courseService.getCourseById(1L).getId());
        final var courseWithUser = courseService.getCourseById(1L);

        assertThat(courseWithUser.getUsers()).contains(testUser);

        assertThatThrownBy(() -> {
            courseService.setUserCourseConnection(testUser.getId(), courseRepository.getById(999L).getId());
        }).isInstanceOf(NotFoundException.class);
    }

    @Test
    void shouldRemoveUserCourseConnectionForSameUser() throws Exception {
        userRepository.save(new User("Test_user"));
        final var testUser = userRepository.findUserByUsername("Test_user").get();
        courseService.setUserCourseConnection(testUser.getId(), courseService.getCourseById(1L).getId());

        courseService.removeUserCourseConnection(testUser.getId(), 1L, "Test_user", false);
        final var courseWithOutUser = courseService.getCourseById(1L);
        assertThat(courseWithOutUser.getUsers()).doesNotContain(testUser);

        assertThatThrownBy(() -> {
            courseService.removeUserCourseConnection(testUser.getId(), 999L, "Test_user", false);
        }).isInstanceOf(NotFoundException.class);
    }

    @Test
    void shouldThrowExceptionWhenRemoveUserCourseConnection() throws Exception {
        var testUser = new User("Test_user");
        userRepository.save(testUser);
        testUser = userRepository.findUserByUsername("Test_user").get();
        courseService.setUserCourseConnection(testUser.getId(), courseService.getCourseById(1L).getId());
        final var testUser2 = new User("Another user");
        userRepository.save(testUser2);
        final var anotherUser = userRepository.findUserByUsername("Another user").get();
        courseService.setUserCourseConnection(anotherUser.getId(), courseService.getCourseById(1L).getId());

        assertThatThrownBy(() -> {
            courseService.removeUserCourseConnection(anotherUser.getId(), 1L, "Test_user", false);
        }).isInstanceOf(AccessDeniedException.class);
    }

    @Test
    void shouldRemoveUserCourseConnectionAdmin() {
        var testUser = new User("Test_user");
        userRepository.save(testUser);
        testUser = userRepository.findUserByUsername("Test_user").get();
        courseService.setUserCourseConnection(testUser.getId(), courseService.getCourseById(1L).getId());

        final var testUser2 = new User("Another user");
        userRepository.save(testUser2);

        final var anotherUser = userRepository.findUserByUsername("Another user").get();
        courseService.setUserCourseConnection(anotherUser.getId(), courseService.getCourseById(1L).getId());

        courseService.removeUserCourseConnection(anotherUser.getId(), 1L, "Test_user", true);
        final var courseWithOutUser = courseService.getCourseById(1L);
        assertThat(courseWithOutUser.getUsers()).doesNotContain(anotherUser);
    }
}