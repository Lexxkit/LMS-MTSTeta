package com.alekseenko.lms.controller;

import com.alekseenko.lms.constants.RoleConstants;
import com.alekseenko.lms.dto.CourseDto;
import com.alekseenko.lms.exception.NotFoundException;
import com.alekseenko.lms.service.CourseImageService;
import com.alekseenko.lms.service.CourseService;
import com.alekseenko.lms.service.ModuleService;
import com.alekseenko.lms.service.UserService;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
@RequestMapping("/course")
public class CourseController {

  // Constant, which defines number of elements on each page
  private final static int ITEMS_PER_PAGE = 3;

  private static final Logger logger = LoggerFactory.getLogger(UserProfileController.class);
  private final CourseService courseService;
  private final CourseImageService courseImageService;
  private final UserService userService;
  private final ModuleService moduleService;


  @Autowired
  public CourseController(CourseService courseService,
      CourseImageService courseImageService,
      UserService userService, ModuleService moduleService) {
    this.courseService = courseService;
    this.courseImageService = courseImageService;
    this.userService = userService;
    this.moduleService = moduleService;
  }

  @GetMapping
  public String courseTable(Model model,
      @RequestParam(name = "titlePrefix", required = false) String titlePrefix) {
    return viewPaginated(model, 1, titlePrefix);
  }

  @GetMapping("/page/{pageNumber}")
  public String viewPaginated(Model model,
      @PathVariable ("pageNumber") int pageNumber,
      @RequestParam(name = "titlePrefix", required = false) String titlePrefix) {

    Page<CourseDto> page = courseService.findPaginated(pageNumber, ITEMS_PER_PAGE, titlePrefix);

    model.addAttribute("currentPage", pageNumber);
    model.addAttribute("totalPages", page.getTotalPages());
    model.addAttribute("totalItems", page.getTotalElements());
    model.addAttribute("courses", page.getContent());

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
  public String courseForm(Model model, @PathVariable("id") Long id,
      Authentication auth) {
    CourseDto currentCourse = courseService.getCourseById(id);
    model.addAttribute("activePage", "courses");

    if (auth == null || !auth.isAuthenticated()) {
      model.addAttribute("isReadOnly", "true");
    }

    model.addAttribute("modules", moduleService.findAllByCourse(currentCourse));
    model.addAttribute("course", currentCourse);
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
    model.addAttribute("users", userService.getUsers(id, request));
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
        principal.getName()
    );
    return String.format("redirect:/course/%d", courseId);
  }

  @Secured({RoleConstants.ROLE_OWNER, RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_TUTOR})
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

  @Secured({RoleConstants.ROLE_OWNER, RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_TUTOR})
  @PostMapping("/{id}/picture")
  public String updateCourseImage(@PathVariable("id") Long courseId,
      @RequestParam("courseImage") MultipartFile courseImage) {
    courseImageService.saveCourseImage(courseId, courseImage);
    return String.format("redirect:/course/%d", courseId);
  }
}
