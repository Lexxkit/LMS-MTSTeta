package com.alekseenko.lms.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.alekseenko.lms.dao.UserRepository;
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
public class UserMapperTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @MockBean
    RegistrationListener registrationListener;

    private User TEST_USER;

    @BeforeAll
    void setUp() {
        TEST_USER = new User(1L, "Test", "", Set.of());
        var auth = new UsernamePasswordAuthenticationToken(TEST_USER, null);
        SecurityContextHolder.getContext().setAuthentication(auth);
        userRepository.save(TEST_USER);
    }

    @BeforeEach
    void setAuth() {
        var auth = new UsernamePasswordAuthenticationToken(TEST_USER, null);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    void shouldReturnUserDtoObject() {
        final var testUserDto = userMapper.mapToUserDto(
            userRepository.findUserByUsername("Test").get());
        assertThat(testUserDto).isInstanceOf(UserDto.class);
        assertThat(testUserDto.getUsername()).isEqualTo("Test");
    }

    @Test
    void shouldReturnUserObjectFromDto() {
        final var testUserDto = new UserDto(1L, "Test_Dto", "", Set.of());
        final var testUser = userMapper.mapToUser(testUserDto);

        assertThat(testUser).isInstanceOf(User.class);
        assertThat(testUser.getUsername()).isEqualTo("Test_Dto");
    }
}
