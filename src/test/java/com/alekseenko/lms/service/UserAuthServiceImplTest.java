package com.alekseenko.lms.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.alekseenko.lms.dao.RoleRepository;
import com.alekseenko.lms.dao.UserRepository;
import com.alekseenko.lms.domain.Role;
import com.alekseenko.lms.domain.User;
import java.util.List;
import java.util.Set;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Disabled
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

    @BeforeAll
    void setUp() {
        roleRepository.deleteAll();
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleStudent = new Role("ROLE_STUDENT");
        roleRepository.saveAll(List.of(roleAdmin, roleStudent));
        User testUser = new User(1L, "Test", "", Set.of(roleRepository.findRoleByName("ROLE_ADMIN").get()));
        userRepository.save(testUser);
    }

    @Test
    void shouldLoadUserByUserName() throws Exception {
        final var user = userDetailsService.loadUserByUsername("Test");
        assertThat(user.getUsername()).isEqualTo("Test");
        assertThatThrownBy(() -> {
            userDetailsService.loadUserByUsername("Anonimus");}
        )
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("User not found");
    }
}
