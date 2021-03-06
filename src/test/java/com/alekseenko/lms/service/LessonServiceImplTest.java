package com.alekseenko.lms.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.alekseenko.lms.dao.CourseRepository;
import com.alekseenko.lms.dao.LessonRepository;
import com.alekseenko.lms.dao.ModuleRepository;
import com.alekseenko.lms.domain.Course;
import com.alekseenko.lms.domain.Lesson;
import com.alekseenko.lms.domain.Module;
import com.alekseenko.lms.domain.User;
import com.alekseenko.lms.dto.LessonDto;
import com.alekseenko.lms.event.RegistrationListener;
import com.alekseenko.lms.exception.NotFoundException;
import java.util.Set;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestInstance(Lifecycle.PER_CLASS)
@Transactional
public class LessonServiceImplTest {

  @Autowired
  private LessonRepository lessonRepository;
  @Autowired
  private CourseRepository courseRepository;
  @Autowired
  private ModuleRepository moduleRepository;
  @Autowired
  private LessonService lessonService;
  @MockBean
  RegistrationListener registrationListener;


  private User TEST_USER;

  @BeforeAll
  void setUp() {
    TEST_USER = new User(1L, "Test", "", Set.of());
    var auth = new UsernamePasswordAuthenticationToken(TEST_USER, null);
    SecurityContextHolder.getContext().setAuthentication(auth);
    var course1 = new Course(1L, "Oleg", "Learning test", null);
    courseRepository.save(course1);
    var module1 = new Module(1L, "Learning test", "Desc", course1, null);
    moduleRepository.save(module1);
    var lesson = new Lesson(1L, "Title", "Text", module1);
    lessonRepository.save(lesson);
  }

  @BeforeEach
  void setAuth() {
    var auth = new UsernamePasswordAuthenticationToken(TEST_USER, null);
    SecurityContextHolder.getContext().setAuthentication(auth);
  }

  @Test
  void shouldFindLessonById() throws Exception {
    final var lesson = lessonService.getLessonById(1L);
    assertThat(lesson.getTitle()).isEqualTo("Title");
    assertThat(lesson.getContent()).isEqualTo("Text");
    assertThatThrownBy(() -> {
      lessonService.getLessonById(999L);
    }).isInstanceOf(NotFoundException.class);
  }

  @Test
  void shouldFindAllForLessonIdWithoutText() {
    final var lessons = lessonService.getAllForLessonIdWithoutText(1L);
    assertThat(lessons.size()).isEqualTo(1);
    assertThat(lessons.get(0).getContent()).isNull();
  }

  @Test
  void shouldSaveLessonToDB() {
    final var lesson = new LessonDto(2L, "New title", "New text",
        courseRepository.getById(1L).getId());
    lessonService.saveLesson(lesson);
    assertThat(lessonRepository.findAll().size()).isEqualTo(2);
    assertThat(lessonRepository.getById(2L).getTitle()).isEqualTo("New title");
  }

  @Test
  void shouldDeleteLessonFromDB() {
    var lesson = lessonRepository.findById(1L);
    assertThat(lesson).isPresent();

    lessonService.deleteLesson(1L);

    lesson = lessonRepository.findById(1L);
    assertThat(lesson).isEmpty();

    final var lessons = lessonRepository.findAll();
    assertThat(lessons.size()).isEqualTo(0);
  }
}
