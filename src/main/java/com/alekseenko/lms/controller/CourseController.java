package com.alekseenko.lms.controller;

import com.alekseenko.lms.domain.Course;
import com.alekseenko.lms.service.CourseService;
import com.alekseenko.lms.service.StatisticsCounter;
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
    private final StatisticsCounter statisticsCounter;

    @Autowired
    public CourseController(CourseService courseService, StatisticsCounter statisticsCounter) {
        this.courseService = courseService;
        this.statisticsCounter = statisticsCounter;
    }

    @GetMapping
    public String courseTable(Model model, @RequestParam(name = "titlePrefix", required = false) String titlePrefix) {
        statisticsCounter.countHandlerCall();
        List<Course> coursesList = courseService.getCoursesByTitleWithPrefix(titlePrefix == null ? "" : titlePrefix);
        model.addAttribute("courses", coursesList);
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
        model.addAttribute("course", courseService.getCourseById(id)
                .orElseThrow(NotFoundException::new));
        return "course-form";
    }

    @DeleteMapping("/{id}")
    public String deleteCourse(@PathVariable("id") Long id) {
        courseService.deleteCourse(id);
        return "redirect:/course";
    }

    @GetMapping("/new")
    public String courseForm(Model model) {
        model.addAttribute("course", courseService.createCourse());
        return "course-form";
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}
