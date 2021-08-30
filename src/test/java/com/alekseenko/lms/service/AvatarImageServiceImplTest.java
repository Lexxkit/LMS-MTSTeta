package com.alekseenko.lms.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.alekseenko.lms.dao.AvatarImageRepository;
import com.alekseenko.lms.dao.UserRepository;
import com.alekseenko.lms.domain.AvatarImage;
import com.alekseenko.lms.domain.User;
import com.alekseenko.lms.event.RegistrationListener;
import com.alekseenko.lms.exception.NotFoundException;
import java.util.List;
import java.util.Set;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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
  RegistrationListener registrationListener;

  @Value("${file.storage.avatar.path}")
  private String path;

  @Value("${file.storage.img.path.default}")
  private String defaultImgPath;

  private User TEST_USER;

  @BeforeAll
  void setUp() {
    TEST_USER = new User(1L, "Test", "", Set.of());
    var auth = new UsernamePasswordAuthenticationToken(TEST_USER, null);
    SecurityContextHolder.getContext().setAuthentication(auth);
    User testUser = new User("Test_user");
    User testUserWithoutAvatar = new User("Another_test_user");
    testUser.setEnabled(true);
    testUserWithoutAvatar.setEnabled(true);
    userRepository.saveAll(List.of(testUser, testUserWithoutAvatar));
    AvatarImage avatarImage = new AvatarImage(1L, "jpeg", "Test",
        userRepository.findUserByUsername("Test_user").get());
    avatarImageRepository.save(avatarImage);
  }

  @BeforeEach
  void setAuth() {
    var auth = new UsernamePasswordAuthenticationToken(TEST_USER, null);
    SecurityContextHolder.getContext().setAuthentication(auth);
  }

  @Test
  void shouldReturnContentTypeByUser() throws Exception {
    final var contentType = avatarImageService.getContentTypeByUser("Test_user");

    assertThat(contentType).isEqualTo("jpeg");
  }

  @Test
  void shouldThrowNotFoundExceptionByWrongUser() throws Exception {
    assertThatThrownBy(() -> {
      avatarImageService.getContentTypeByUser("");
    }).isInstanceOf(NotFoundException.class);
  }
}
