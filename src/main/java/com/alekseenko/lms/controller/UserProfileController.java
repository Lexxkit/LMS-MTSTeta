package com.alekseenko.lms.controller;

import com.alekseenko.lms.exception.InternalServerException;
import com.alekseenko.lms.exception.NotFoundException;
import com.alekseenko.lms.service.AvatarImageService;
import com.alekseenko.lms.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/profile")
public class UserProfileController {

  private static final Logger logger = LoggerFactory.getLogger(UserProfileController.class);
  private final AvatarImageService avatarImageService;
  private final CourseService courseService;

  @Autowired
  public UserProfileController(AvatarImageService avatarImageService, CourseService courseService) {
    this.avatarImageService = avatarImageService;
    this.courseService = courseService;
  }

  @GetMapping
  public String getUserProfile(Model model, Authentication auth) {
    if (auth != null) {
      model.addAttribute("courses", courseService.getCoursesForUser(auth.getName()));
      model.addAttribute("avatar", avatarImageService.getAvatarImageByUser(auth.getName()));
      model.addAttribute("activePage", "profile");

      return "user-profile";
    }
    return "redirect:/login";
  }

  @GetMapping("/avatar")
  @ResponseBody
  public ResponseEntity<byte[]> avatarImage(Authentication auth) {
    String contentType = avatarImageService.getContentTypeByUser(auth.getName());
    byte[] data = avatarImageService.getAvatarImageByUser(auth.getName())
        .orElseThrow(() -> new NotFoundException(
            String.format("Avatar for user %s not found", auth.getName())));
    return ResponseEntity
        .ok()
        .contentType(MediaType.parseMediaType(contentType))
        .body(data);

  }

  @PostMapping("/avatar")
  public String updateAvatarImage(Authentication auth,
      @RequestParam("avatar") MultipartFile avatar) {
    if (!avatar.isEmpty()) {
      logger.info("File name {}, file content type {}, file size {}", avatar.getOriginalFilename(),
          avatar.getContentType(), avatar.getSize());
      try {
        avatarImageService
            .saveAvatarImage(auth.getName(), avatar.getContentType(), avatar.getInputStream());
      } catch (Exception ex) {
        logger.info("", ex);
        throw new InternalServerException("Internal Server Error");
      }
    }
    return "redirect:/profile";
  }
}
