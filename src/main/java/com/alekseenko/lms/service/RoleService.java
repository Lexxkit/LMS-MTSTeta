package com.alekseenko.lms.service;

import com.alekseenko.lms.dto.RoleDto;
import java.util.List;

public interface RoleService {

  List<RoleDto> findAllRoles();

  RoleDto getRoleByName(String name);
}
