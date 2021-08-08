package com.alekseenko.lms.service;

import com.alekseenko.lms.dao.CourseRepository;
import com.alekseenko.lms.dao.RoleRepository;
import com.alekseenko.lms.dao.UserRepository;
import com.alekseenko.lms.domain.Course;
import com.alekseenko.lms.domain.Role;
import com.alekseenko.lms.domain.User;
import com.alekseenko.lms.dto.UserDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

import static com.alekseenko.lms.RoleConstants.ROLE_ADMIN;
import static com.alekseenko.lms.RoleConstants.ROLE_STUDENT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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
    private MyEventListener myEventListener;

    @BeforeAll
    void setUp() {
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleStudent = new Role("ROLE_STUDENT");
        roleRepository.saveAll(List.of(roleAdmin, roleStudent));
        Course course = new Course(1L, "Oleg", "New course");
        courseRepository.save(course);
        User testUser = new User(1L, "Test", "", Set.of());
        userRepository.save(testUser);
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
    void shouldSaveUserToDB() {
        final var userToSave = new User(2L, "New User", "", Set.of(roleRepository.findRoleByName(ROLE_ADMIN).get()));
        userService.saveUser(userMapper.mapToUserDto(userToSave));
        assertThat(userRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    void shouldSaveUserToDBAndAssignRole() {
        final var userWithoutRole = new User("No Role");
        userService.saveUser(userMapper.mapToUserDto(userWithoutRole));
        assertThat(userRepository.findAll().size()).isEqualTo(2);

        final var savedUser = userRepository.findUserByUsername("No Role").get();
        assertThat(savedUser.getRoles().size()).isNotNull();
        assertThat(roleRepository.findRoleByName(ROLE_STUDENT).get()).isIn(savedUser.getRoles());
    }

    @Test
    void shouldReturnListWithSingleUser() {
        final var oneUserList = userService.assignSingleUserToCourse("Test");
        assertThat(oneUserList.size()).isEqualTo(1);
    }
}
