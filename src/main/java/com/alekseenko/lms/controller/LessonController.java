package com.alekseenko.lms.controller;

import com.alekseenko.lms.constants.RoleConstants;
import com.alekseenko.lms.dto.LessonDto;
import com.alekseenko.lms.service.LessonService;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
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
@RequestMapping("/lesson")
@AllArgsConstructor
public class LessonController {

  private final LessonService lessonService;

  @Secured({RoleConstants.ROLE_OWNER, RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_TUTOR})
  @GetMapping("/new")
  public String lessonNewForm(Model model, @RequestParam("module_id") Long moduleId) {
    model.addAttribute("lessonDto", new LessonDto(moduleId));
    return "lesson-form";
  }

  @Secured({RoleConstants.ROLE_OWNER, RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_TUTOR})
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

  @Secured({RoleConstants.ROLE_OWNER, RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_TUTOR})
  @DeleteMapping("/{id}")
  public String deleteLesson(@PathVariable("id") Long id,
      @RequestParam("courseId") Long courseId) {
    lessonService.deleteLesson(id);
    return String.format("redirect:/course/%d", courseId);
  }
}
