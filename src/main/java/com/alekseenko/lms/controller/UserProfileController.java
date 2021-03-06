package com.alekseenko.lms.controller;

import com.alekseenko.lms.constants.RoleConstants;
import com.alekseenko.lms.dto.RoleDto;
import com.alekseenko.lms.dto.UserDto;
import com.alekseenko.lms.exception.NotFoundException;
import com.alekseenko.lms.service.AvatarImageService;
import com.alekseenko.lms.service.CourseService;
import com.alekseenko.lms.service.RoleService;
import com.alekseenko.lms.service.UserService;
import java.security.Principal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/profile")
@AllArgsConstructor
public class UserProfileController {

  private final AvatarImageService avatarImageService;
  private final CourseService courseService;
  private final RoleService roleService;
  private final UserService userService;

  @ModelAttribute("roles")
  public List<RoleDto> rolesAttribute() {
    return roleService.findAllRoles();
  }

  @GetMapping
  public String getUserProfile(Model model, Authentication auth) {
    if (auth != null) {
      model.addAttribute("courses", courseService.getCoursesForUser(auth.getName()));
      model.addAttribute("avatar", avatarImageService.getAvatarImageByUser(auth.getName()));
      model.addAttribute("user", userService.getUserByUsername(auth.getName()));
      model.addAttribute("activePage", "profile");

      return "user-profile";
    }
    return "redirect:/login";
  }

  @PostMapping
  public String registerUser(@Valid @ModelAttribute("user") UserDto user,
      BindingResult bindingResult, Model model, Authentication auth) {
    if (bindingResult.hasErrors()) {
      model.addAttribute("courses", courseService.getCoursesForUser(auth.getName()));
      model.addAttribute("avatar", avatarImageService.getAvatarImageByUser(auth.getName()));
      model.addAttribute("activePage", "profile");
      return "user-profile";
    }

    userService.saveUser(user);
    return "redirect:/login";
  }

  @GetMapping("/avatar")
  @ResponseBody
  public ResponseEntity<byte[]> avatarImage(Authentication auth) {
    String username = auth.getName();
    byte[] data = avatarImageService.getDataAvatar(username)
        .orElseThrow(() -> new NotFoundException("avatar_not_found"));
    String contentType = avatarImageService.getContentTypeAvatarByUser(username)
        .orElseThrow(() -> new NotFoundException("content_type_undefined"));

    return ResponseEntity
        .ok()
        .contentType(MediaType.parseMediaType(contentType))
        .body(data);
  }

  @PostMapping("/avatar")
  public String updateAvatarImage(Authentication auth,
      @RequestParam("avatar") MultipartFile avatar) {
    avatarImageService.saveAvatarImage(auth.getName(), avatar);
    return "redirect:/profile";
  }

  @PreAuthorize("isAuthenticated()")
  @DeleteMapping("/{courseId}/unassign")
  public String unassignUserFromCourse(@PathVariable("courseId") Long courseId,
      @RequestParam("userId") Long userId,
      Principal principal,
      HttpServletRequest request) {

    courseService.removeUserCourseConnection(userId,
        courseId,
        principal.getName(),
        request.isUserInRole(RoleConstants.ROLE_STUDENT)
    );
    return String.format("redirect:/profile");
  }
}
