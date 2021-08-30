package com.alekseenko.lms.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.alekseenko.lms.dao.CourseRepository;
import com.alekseenko.lms.dao.RoleRepository;
import com.alekseenko.lms.dao.UserRepository;
import com.alekseenko.lms.domain.Course;
import com.alekseenko.lms.domain.User;
import com.alekseenko.lms.dto.UserDto;
import com.alekseenko.lms.event.RegistrationListener;
import com.alekseenko.lms.mapper.UserMapper;
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
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestInstance(Lifecycle.PER_CLASS)
@Transactional
public class UserServiceImplTest {

  @Autowired
  private CourseRepository courseRepository;
  @Autowired
  private RoleRepository roleRepository;
  @Autowired
  private UserMapper userMapper;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private UserService userService;
  @MockBean
  RegistrationListener registrationListener;

  private User TEST_USER;

  @BeforeAll
  void setUp() {
    TEST_USER = new User(1L, "Test", "", Set.of());
    var auth = new UsernamePasswordAuthenticationToken(TEST_USER, null);
    SecurityContextHolder.getContext().setAuthentication(auth);
    Course course = new Course(1L, "Oleg", "New course", null);
    courseRepository.save(course);
    userRepository.save(TEST_USER);
  }

  @BeforeEach
  void setAuth() {
    var auth = new UsernamePasswordAuthenticationToken(TEST_USER, null);
    SecurityContextHolder.getContext().setAuthentication(auth);
  }

  @Test
  void shouldFindAllUsers() {
    final var users = userService.findAllUsers();
    assertThat(users.size()).isEqualTo(1);
  }

  @Test
  void shouldFindUsersNotAssignedToCourse() {
    final var users = userService.getUsersNotAssignedToCourse(1L);
    assertThat(users.size()).isEqualTo(1);
    assertThat(users.get(0).getCourses()).isNull();
  }

  @Test
  void shouldFindUserById() {
    final var user = userService.getUserById(1L);
    assertThat(user.getUsername()).isEqualTo("Test");
  }

  @Test
  void shouldFindUserByUsername() {
    final var user = userService.getUserByUsername("Test");
    assertThat(user.getUsername()).isEqualTo("Test");
  }

  @Test
  void shouldReturnEmptyUserDtoObject() {
    final var user = userService.getRegistrationTemplate();
    assertThat(user).isInstanceOf(UserDto.class);
    assertThat(user.getUsername()).isNull();
  }

  @Test
  void shouldReturnListWithSingleUser() {
    final var oneUserList = userService.assignSingleUserToCourse("Test");
    assertThat(oneUserList.size()).isEqualTo(1);
  }
}
