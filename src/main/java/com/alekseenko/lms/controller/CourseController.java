package com.alekseenko.lms.controller;

import com.alekseenko.lms.domain.Course;
import com.alekseenko.lms.dto.CourseDto;
import com.alekseenko.lms.service.CourseService;
import com.alekseenko.lms.service.LessonService;
import com.alekseenko.lms.service.StatisticsCounter;
import com.alekseenko.lms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;
    private final LessonService lessonService;
    private final UserService userService;
    private final StatisticsCounter statisticsCounter;

    @Autowired
    public CourseController(CourseService courseService,
                            LessonService lessonService,
                            UserService userService,
                            StatisticsCounter statisticsCounter) {
        this.courseService = courseService;
        this.lessonService = lessonService;
        this.userService = userService;
        this.statisticsCounter = statisticsCounter;
    }

    @GetMapping
    public String courseTable(Model model,
                              @RequestParam(name = "titlePrefix", required = false) String titlePrefix) {
        statisticsCounter.countHandlerCall();
        model.addAttribute("activePage", "courses");
        if (titlePrefix == null) {
            model.addAttribute("courses", courseService.getAllCourses());
        } else {
            model.addAttribute("courses", courseService.getCoursesByTitleWithPrefix(titlePrefix + "%"));
        }
        return "index";
    }

    @PostMapping
    public String submitCourseForm(@Valid @ModelAttribute("course")CourseDto courseDto, BindingResult bindingResult) {
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
        model.addAttribute("lessons", lessonService.getAllForLessonIdWithoutText(currentCourse.getId()));
        model.addAttribute("users", currentCourse.getUsers());
        return "course-form";
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public String deleteCourse(@PathVariable("id") Long id) {
        courseService.deleteCourse(id);
        return "redirect:/course";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/assign")
    public String assignCourseToUser(Model model, HttpServletRequest request, @PathVariable("id") Long id) {
        model.addAttribute("activePage", "courses");
        model.addAttribute("courseId", id);
        if (request.isUserInRole("ROLE_ADMIN")) {
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
                                         @RequestParam("userId") Long userId, Principal principal) {

        courseService.removeUserCourseConnection(userId, courseId, principal.getName());
        return String.format("redirect:/course/%d", courseId);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/new")
    public String courseForm(Model model) {
        model.addAttribute("course", courseService.getCourseTemplate());
        return "course-form";
    }
}
