package com.alekseenko.lms.dao;

import com.alekseenko.lms.domain.Role;
import com.alekseenko.lms.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findRoleByName(String name);
}
