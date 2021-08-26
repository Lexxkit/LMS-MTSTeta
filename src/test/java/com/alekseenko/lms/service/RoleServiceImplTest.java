package com.alekseenko.lms.service;

import static com.alekseenko.lms.constants.RoleConstants.ROLE_ADMIN;
import static com.alekseenko.lms.constants.RoleConstants.ROLE_STUDENT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.alekseenko.lms.dao.RoleRepository;
import com.alekseenko.lms.domain.Role;
import com.alekseenko.lms.event.RegistrationListener;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@Transactional
public class RoleServiceImplTest {

  @Autowired
  private RoleRepository roleRepository;
  @Autowired
  private RoleService roleService;
  @MockBean
  RegistrationListener registrationListener;

  @BeforeEach
  void setUp() {
    if (roleRepository.findAll().size() != 2) {
      roleRepository.deleteAll();
      Role roleAdmin = new Role("ROLE_ADMIN");
      Role roleStudent = new Role("ROLE_STUDENT");
      roleRepository.saveAll(List.of(roleAdmin, roleStudent));
    }
  }

  @Test
  void shouldReturnAllRoles() {
    final var roles = roleService.findAllRoles();
    assertThat(roles.size()).isEqualTo(2);
    assertThat(roles.get(0).getName()).isIn(List.of(ROLE_ADMIN, ROLE_STUDENT));
  }

  @Test
  void shouldReturnRoleByName() {
    final var roleAdmin = roleService.getRoleByName(ROLE_ADMIN);
    final var roleStudent = roleService.getRoleByName(ROLE_STUDENT);
    assertThat(roleAdmin.getName()).isEqualTo("ROLE_ADMIN");
    assertThat(roleStudent.getName()).isEqualTo("ROLE_STUDENT");
  }
}
