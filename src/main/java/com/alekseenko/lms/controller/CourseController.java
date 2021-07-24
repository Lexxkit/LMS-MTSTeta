package com.alekseenko.lms.controller;

import com.alekseenko.lms.domain.Course;
import com.alekseenko.lms.domain.User;
import com.alekseenko.lms.service.CourseService;
import com.alekseenko.lms.service.LessonService;
import com.alekseenko.lms.service.StatisticsCounter;
import com.alekseenko.lms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

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
    public String courseTable(Model model, @RequestParam(name = "titlePrefix", required = false) String titlePrefix) {
        statisticsCounter.countHandlerCall();
        if (titlePrefix == null) {
            model.addAttribute("courses", courseService.getAllCourses());
        } else {
            model.addAttribute("courses", courseService.getCoursesByTitleWithPrefix(titlePrefix + "%"));
        }
        model.addAttribute("activePage", "courses");
        return "index";
    }

    @PostMapping
    public String submitCourseForm(@Valid Course course, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "course-form";
        }
        courseService.saveCourse(course);
        return "redirect:/course";
    }

    @RequestMapping("/{id}")
    public String courseForm(Model model, @PathVariable("id") Long id) {
        Course currentCourse = courseService.getCourseById(id)
                .orElseThrow(NotFoundException::new);
        model.addAttribute("course", currentCourse);
        model.addAttribute("lessons", lessonService.getAllForLessonIdWithoutText(currentCourse.getId()));
        model.addAttribute("users", currentCourse.getUsers());
        return "course-form";
    }

    @DeleteMapping("/{id}")
    public String deleteCourse(@PathVariable("id") Long id) {
        courseService.deleteCourse(id);
        return "redirect:/course";
    }

    @GetMapping("/{id}/assign")
    public String assignCourseToUser(Model model, @PathVariable("id") Long id) {

        List<User> users = userService.getUsersNotAssignedToCourse(id);

        model.addAttribute("users", users);
        model.addAttribute("courseId", id);
        return "course-assign";
    }

    @PostMapping("/{courseId}/assign")
    public String assignUserForm(@PathVariable("courseId") Long courseId,
                                 @RequestParam("userId") Long userId) {
        User user = userService.getUserById(userId)
                .orElseThrow(NotFoundException::new);
        Course course = courseService.getCourseById(courseId)
                .orElseThrow(NotFoundException::new);

        courseService.setUserCourseConnection(user, course);
        return "redirect:/course";
    }

    @DeleteMapping("/{courseId}/unassign")
    public String unassignUserFromCourse(@PathVariable("courseId") Long courseId,
                                         @RequestParam("userId") Long userId) {
        User user = userService.getUserById(userId)
                .orElseThrow(NotFoundException::new);
        Course course = courseService.getCourseById(courseId)
                .orElseThrow(NotFoundException::new);

        courseService.removeUserCourseConnection(user, course);
        return String.format("redirect:/course/%d", courseId);
    }

    @GetMapping("/new")
    public String courseForm(Model model) {
        model.addAttribute("course", courseService.getCourseTemplate());
        return "course-form";
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}
