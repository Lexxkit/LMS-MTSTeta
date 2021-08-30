package com.alekseenko.lms.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.alekseenko.lms.dao.RoleRepository;
import com.alekseenko.lms.dao.UserRepository;
import com.alekseenko.lms.domain.Role;
import com.alekseenko.lms.domain.User;
import com.alekseenko.lms.event.RegistrationListener;
import java.util.List;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class UserAuthServiceImplTest {

  @Autowired
  private RoleRepository roleRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserDetailsService userDetailsService;
  @MockBean
  RegistrationListener registrationListener;


  private User TEST_USER;

  @BeforeAll
  void setUp() {
    TEST_USER = new User(1L, "Test", "", Set.of());
    var auth = new UsernamePasswordAuthenticationToken(TEST_USER, null);
    SecurityContextHolder.getContext().setAuthentication(auth);
    roleRepository.deleteAll();
    Role roleAdmin = new Role("ROLE_ADMIN");
    Role roleStudent = new Role("ROLE_STUDENT");
    roleRepository.saveAll(List.of(roleAdmin, roleStudent));
    User testUser = TEST_USER;
    testUser.setRoles(Set.of(roleRepository.findRoleByName("ROLE_ADMIN").get()));
    userRepository.save(testUser);
  }

  @BeforeEach
  void setAuth() {
    var auth = new UsernamePasswordAuthenticationToken(TEST_USER, null);
    SecurityContextHolder.getContext().setAuthentication(auth);
  }

  @Test
  void shouldLoadUserByUserName() throws Exception {
    final var user = userDetailsService.loadUserByUsername("Test");
    assertThat(user.getUsername()).isEqualTo("Test");
    assertThatThrownBy(() -> {
          userDetailsService.loadUserByUsername("Anonimus");
        }
    )
        .isInstanceOf(UsernameNotFoundException.class)
        .hasMessage("User not found");
  }
}
