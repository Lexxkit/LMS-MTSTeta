package com.alekseenko.lms.controller;

import com.alekseenko.lms.RoleConstants;
import com.alekseenko.lms.dto.CourseDto;
import com.alekseenko.lms.exception.InternalServerException;
import com.alekseenko.lms.exception.NotFoundException;
import com.alekseenko.lms.service.CourseImageService;
import com.alekseenko.lms.service.CourseService;
import com.alekseenko.lms.service.LessonService;
import com.alekseenko.lms.service.UserService;
import java.io.IOException;
import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/course")
public class CourseController {

  private static final Logger logger = LoggerFactory.getLogger(UserProfileController.class);
  private final CourseService courseService;
  private final CourseImageService courseImageService;
  private final LessonService lessonService;
  private final UserService userService;

  @Autowired
  public CourseController(CourseService courseService,
      CourseImageService courseImageService,
      LessonService lessonService,
      UserService userService) {
    this.courseService = courseService;
    this.courseImageService = courseImageService;
    this.lessonService = lessonService;
    this.userService = userService;
  }

  @GetMapping
  public String courseTable(Model model,
      @RequestParam(name = "titlePrefix", required = false) String titlePrefix) {
    model.addAttribute("activePage", "courses");
    if (titlePrefix == null) {
      model.addAttribute("courses", courseService.getAllCourses());
    } else {
      model.addAttribute("courses", courseService.getCoursesByTitleWithPrefix(titlePrefix + "%"));
    }
    return "index";
  }

  @PostMapping
  public String submitCourseForm(@Valid @ModelAttribute("course") CourseDto courseDto,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "course-form";
    }
    courseService.saveCourse(courseDto);
    return "redirect:/course";
  }

  @RequestMapping("/{id}")
  public String courseForm(Model model, @PathVariable("id") Long id) {
    CourseDto currentCourse = courseService.getCourseById(id);

    model.addAttribute("activePage", "courses");
    model.addAttribute("course", currentCourse);
    model
        .addAttribute("lessons", lessonService.getAllForLessonIdWithoutText(currentCourse.getId()));
    model.addAttribute("users", currentCourse.getUsers());
    return "course-form";
  }

  @Secured(RoleConstants.ROLE_ADMIN)
  @DeleteMapping("/{id}")
  public String deleteCourse(@PathVariable("id") Long id) {
    courseService.deleteCourse(id);
    return "redirect:/course";
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/{id}/assign")
  public String assignCourseToUser(Model model, HttpServletRequest request,
      @PathVariable("id") Long id) {
    model.addAttribute("activePage", "courses");
    model.addAttribute("courseId", id);
    if (request.isUserInRole(RoleConstants.ROLE_ADMIN)) {
      model.addAttribute("users", userService.getUsersNotAssignedToCourse(id));
    } else {
      model.addAttribute("users", userService.assignSingleUserToCourse(request.getRemoteUser()));
    }
    return "course-assign";
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/{courseId}/assign")
  public String assignUserForm(@PathVariable("courseId") Long courseId,
      @RequestParam("userId") Long userId) {

    courseService.setUserCourseConnection(userId, courseId);
    return "redirect:/course";
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
        request.isUserInRole(RoleConstants.ROLE_ADMIN));
    return String.format("redirect:/course/%d", courseId);
  }

  @Secured(RoleConstants.ROLE_ADMIN)
  @GetMapping("/new")
  public String courseForm(Model model) {
    model.addAttribute("course", courseService.getCourseTemplate());
    return "course-form";
  }

  @GetMapping("/{id}/picture")
  @ResponseBody
  public ResponseEntity<byte[]> courseImage(@PathVariable("id") Long courseId) {
    String contentType = courseImageService.getContentTypeByCourse(courseId);
    byte[] data = courseImageService.getCourseImageByCourse(courseId)
        .orElseThrow(
            () -> new NotFoundException(String.format("Course with id#%d not found", courseId)));
    return ResponseEntity
        .ok()
        .contentType(MediaType.parseMediaType(contentType))
        .body(data);
  }

  @Secured(RoleConstants.ROLE_ADMIN)
  @PostMapping("/{id}/picture")
  public String updateCourseImage(@PathVariable("id") Long courseId,
      @RequestParam("courseImage") MultipartFile courseImage) {
    if (!courseImage.isEmpty()) {
      logger.info("File name {}, file content type {}, file size {}",
          courseImage.getOriginalFilename(), courseImage.getContentType(), courseImage.getSize());
      try {
        courseImageService
            .saveCourseImage(courseId, courseImage.getContentType(), courseImage.getInputStream());
      } catch (IOException ex) {
        logger.info("Read file error", ex);
        throw new InternalServerException("Internal server error");
      }
    }
    return String.format("redirect:/course/%d", courseId);
  }
}
