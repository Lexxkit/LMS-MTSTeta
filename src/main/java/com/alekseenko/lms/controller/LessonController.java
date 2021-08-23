package com.alekseenko.lms.controller;

import com.alekseenko.lms.constants.RoleConstants;
import com.alekseenko.lms.dto.LessonDto;
import com.alekseenko.lms.service.LessonService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Secured(RoleConstants.ROLE_ADMIN)
@RequestMapping("/lesson")
public class LessonController {

  private final LessonService lessonService;

  @Autowired
  public LessonController(LessonService lessonService) {
    this.lessonService = lessonService;
  }

  @GetMapping("/new")
  public String lessonNewForm(Model model, @RequestParam("module_id") Long moduleId) {
    model.addAttribute("lessonDto", new LessonDto(moduleId));
    return "lesson-form";
  }

  @PostMapping
  public String submitLessonForm(@Valid LessonDto lessonDto, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "lesson-form";
    }
    lessonService.saveLesson(lessonDto);
    return String.format("redirect:/module/%d", lessonDto.getModuleId());
  }


  @RequestMapping("/{id}")
  public String lessonForm(Model model, @PathVariable("id") Long id) {
    model.addAttribute("lessonDto", lessonService.getLessonById(id));
    return "lesson-form";
  }

  @DeleteMapping("/{id}")
  public String deleteLesson(@PathVariable("id") Long id,
      @RequestParam("courseId") Long courseId) {
    lessonService.deleteLesson(id);
    return String.format("redirect:/course/%d", courseId);
  }
}
