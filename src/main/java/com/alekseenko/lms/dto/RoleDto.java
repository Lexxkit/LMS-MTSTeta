package com.alekseenko.lms.dto;

import com.alekseenko.lms.domain.User;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RoleDto {

  private Long id;

  private String name;

  private Set<User> users;

  public RoleDto(String name) {
    this.name = name;
  }

}
