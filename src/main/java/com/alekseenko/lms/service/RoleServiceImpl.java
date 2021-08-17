package com.alekseenko.lms.service;

import com.alekseenko.lms.controller.NotFoundException;
import com.alekseenko.lms.dao.RoleRepository;
import com.alekseenko.lms.dto.RoleDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

  private final RoleRepository roleRepository;

  @Autowired
  public RoleServiceImpl(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

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
        .orElseThrow(NotFoundException::new);
  }
}
