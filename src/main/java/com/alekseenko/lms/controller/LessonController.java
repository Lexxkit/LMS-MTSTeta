package com.alekseenko.lms.controller;

import com.alekseenko.lms.domain.Course;
import com.alekseenko.lms.domain.Lesson;
import com.alekseenko.lms.dto.LessonDto;
import com.alekseenko.lms.service.CourseService;
import com.alekseenko.lms.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/lesson")
public class LessonController {

    private final CourseService courseService;
    private final LessonService lessonService;

    @Autowired
    public LessonController(CourseService courseService, LessonService lessonService) {
        this.courseService = courseService;
        this.lessonService = lessonService;
    }

    @GetMapping("/new")
    public String lessonNewForm(Model model, @RequestParam("course_id") Long courseId) {
        model.addAttribute("lessonDto", new LessonDto(courseId));
        return "lesson-form";
    }

    @PostMapping
    public String submitLessonForm(@Valid LessonDto lessonDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "lesson-form";
        }
        Course currentCourse = courseService.getCourseById(lessonDto.getCourseId())
                .orElseThrow(NotFoundException::new);
        Lesson lesson = new Lesson(
                lessonDto.getId(),
                lessonDto.getTitle(),
                lessonDto.getText(),
                currentCourse
        );
        lessonService.saveLesson(lesson);
        return String.format("redirect:/course/%d", lessonDto.getCourseId());
    }

    @RequestMapping("/{id}")
    public String lessonForm(Model model, @PathVariable("id") Long id) {
        model.addAttribute("lessonDto", lessonService.getLessonById(id)
                .map(l -> new LessonDto(l.getId(), l.getTitle(), l.getText(), l.getCourse().getId()))
                .orElseThrow(NotFoundException::new));
        return "lesson-form";
    }

    @DeleteMapping("/{id}")
    @Transactional
    public String deleteLesson(@PathVariable("id") Long id) {
        Course currentCourse = lessonService.getLessonById(id)
                .orElseThrow(NotFoundException::new)
                .getCourse();
        lessonService.deleteLesson(id);
        return String.format("redirect:/course/%d", currentCourse.getId());
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}
