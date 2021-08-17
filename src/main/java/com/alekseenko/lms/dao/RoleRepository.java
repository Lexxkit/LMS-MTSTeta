package com.alekseenko.lms.dao;

import com.alekseenko.lms.domain.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

  Optional<Role> findRoleByName(String name);
}
