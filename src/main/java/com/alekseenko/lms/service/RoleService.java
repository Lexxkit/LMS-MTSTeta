package com.alekseenko.lms.service;

import com.alekseenko.lms.domain.Role;

import java.util.List;

public interface RoleService {

    List<Role> findAllRoles();

    Role getRoleByName(String name);
}
