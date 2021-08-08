package com.alekseenko.lms.service;

import com.alekseenko.lms.dao.UserRepository;
import com.alekseenko.lms.domain.User;
import com.alekseenko.lms.dto.UserDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.transaction.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
    private MyEventListener myEventListener;

    @BeforeAll
    void setUp() {
        User testUser = new User("Test");
        userRepository.save(testUser);
    }

    @Test
    void shouldReturnUserDtoObject() {
        final var testUserDto = userMapper.mapToUserDto(userRepository.findUserByUsername("Test").get());
        assertThat(testUserDto).isInstanceOf(UserDto.class);
        assertThat(testUserDto.getUsername()).isEqualTo("Test");
    }
}
