package com.alekseenko.lms.service.impl;

import com.alekseenko.lms.dao.RoleRepository;
import com.alekseenko.lms.dto.RoleDto;
import com.alekseenko.lms.exception.NotFoundException;
import com.alekseenko.lms.service.RoleService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

  private final RoleRepository roleRepository;

  @Override
  public List<RoleDto> findAllRoles() {
    return roleRepository.findAll().stream()
        .map(role -> new RoleDto(role.getId(), role.getName(), role.getUsers()))
        .collect(Collectors.toList());
  }

  @Override
  public RoleDto getRoleByName(String name) {
    return roleRepository.findRoleByName(name)
        .map(role -> new RoleDto(role.getName()))
        .orElseThrow(() -> new NotFoundException(String.format("Role %s not found", name)));
  }
}
