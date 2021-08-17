package com.alekseenko.lms.controller;

import com.alekseenko.lms.dto.RoleDto;
import com.alekseenko.lms.dto.UserDto;
import com.alekseenko.lms.service.RoleService;
import com.alekseenko.lms.service.UserService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/user")
public class AdminUserController {

  private final RoleService roleService;
  private final UserService userService;

  @Autowired
  public AdminUserController(RoleService roleService, UserService userService) {
    this.roleService = roleService;
    this.userService = userService;
  }

  @ModelAttribute("roles")
  public List<RoleDto> rolesAttribute() {
    return roleService.findAllRoles();
  }

  @GetMapping
  public String userTable(Model model) {
    model.addAttribute("activePage", "users");
    model.addAttribute("users", userService.findAllUsers());
    return "user-table";
  }

  @PostMapping
  public String submitUserForm(@Valid @ModelAttribute("user") UserDto user,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "user-edit";
    }

    userService.saveUser(user);
    return "redirect:/admin/user";
  }

  @GetMapping("/{id}")
  public String userForm(Model model, @PathVariable("id") Long id) {
    model.addAttribute("activePage", "users");
    model.addAttribute("user", userService.getUserById(id));
    return "user-edit";
  }

}
