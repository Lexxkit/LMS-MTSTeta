package com.alekseenko.lms.controller;

import com.alekseenko.lms.dto.RoleDto;
import com.alekseenko.lms.dto.UserDto;
import com.alekseenko.lms.service.CourseService;
import com.alekseenko.lms.service.RoleService;
import com.alekseenko.lms.service.UserService;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/user")
@AllArgsConstructor
public class AdminUserController {

  private final CourseService courseService;
  private final RoleService roleService;
  private final UserService userService;

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

  @DeleteMapping("/{id}")
  public String deleteUser(@PathVariable("id") Long id, Authentication auth) {
    userService.deleteUser(id, auth.getName());
    return "redirect:/admin/user";
  }

  @GetMapping("/course")
  public String getAllCoursesTable(Model model,
      @RequestParam(name = "titlePrefix", required = false) String titlePrefix,
      @RequestParam(name = "sortField", defaultValue = "title") String sortField,
      @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir) {
    model.addAttribute("activePage", "courses");
    model.addAttribute("courses", courseService.getAllCourses(titlePrefix, sortField, sortDir));

    model.addAttribute("sortField", sortField);
    model.addAttribute("sortDir", sortDir);
    model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

    return "course-table";

  }
}
