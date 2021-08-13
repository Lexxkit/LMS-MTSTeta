package com.alekseenko.lms.service;

import com.alekseenko.lms.controller.NotFoundException;
import com.alekseenko.lms.dao.AvatarImageRepository;
import com.alekseenko.lms.dao.UserRepository;
import com.alekseenko.lms.domain.AvatarImage;
import com.alekseenko.lms.domain.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.transaction.Transactional;

import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class AvatarImageServiceImplTest {
    @MockBean
    private static Logger logger;
    @Autowired
    private AvatarImageRepository avatarImageRepository;
    @Autowired
    private AvatarImageService avatarImageService;
    @Autowired
    private UserRepository userRepository;
    @MockBean
    private MyEventListener myEventListener;

    @Value("${file.storage.path}")
    private String path;

    @BeforeAll
    void setUp() {
        User testUser = new User("Test_user");
        User testUserWithoutAvatar = new User("Another_test_user");
        userRepository.saveAll(List.of(testUser, testUserWithoutAvatar));
        AvatarImage avatarImage = new AvatarImage(1L, "jpeg", "Test", userRepository.findUserByUsername("Test_user").get());
        avatarImageRepository.save(avatarImage);
    }

    @Test
    void shouldReturnContentTypeByUser() throws Exception{
        final var contentType = avatarImageService.getContentTypeByUser("Test_user");

        assertThat(contentType).isEqualTo("jpeg");
    }

    @Test
    void shouldThrowNotFoundExceptionByWrongUser() throws Exception{
        assertThatThrownBy(() -> {
            avatarImageService.getContentTypeByUser("");
        }).isInstanceOf(NotFoundException.class);

    }

    @Test
    void shouldSaveAvatarImage() {
        avatarImageService.saveAvatarImage("Test_user", "png", InputStream.nullInputStream());
        avatarImageService.saveAvatarImage("Another_test_user", "jpeg", InputStream.nullInputStream());

        final var contentTypeForTestUser = avatarImageService.getContentTypeByUser("Test_user");
        final var contentTypeForAnotherUser = avatarImageService.getContentTypeByUser("Another_test_user");

        assertThat(contentTypeForTestUser).isEqualTo("png");
        assertThat(contentTypeForAnotherUser).isEqualTo("jpeg");
    }
}
