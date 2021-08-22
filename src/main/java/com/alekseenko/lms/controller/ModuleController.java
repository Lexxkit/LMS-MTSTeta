package com.alekseenko.lms.controller;

import com.alekseenko.lms.constants.RoleConstants;
import com.alekseenko.lms.dto.ModuleDto;
import com.alekseenko.lms.service.ModuleService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Secured(RoleConstants.ROLE_ADMIN)
@RequestMapping("/module")
public class ModuleController {

  private final ModuleService moduleService;

  @Autowired
  public ModuleController(ModuleService moduleService) {
    this.moduleService = moduleService;
  }

  @GetMapping("/new")
  public String moduleNewForm(Model model, @RequestParam("course_id") Long courseId) {
    model.addAttribute("module", new ModuleDto(courseId));
    return "module-form";
  }

  @PostMapping
  public String submitModuleForm(@Valid ModuleDto moduleDto, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "module-form";
    }
    moduleService.save(moduleDto);
    return String.format("redirect:/course/%d", moduleDto.getCourseId());
  }
//
//
//  @RequestMapping("/{id}")
//  public String lessonForm(Model model, @PathVariable("id") Long id) {
//    model.addAttribute("lessonDto", lessonService.getLessonById(id));
//    return "lesson-form";
//  }
//
//  @DeleteMapping("/{id}")
//  public String deleteLesson(@PathVariable("id") Long id,
//      @RequestParam("courseId") Long courseId) {
//
//    lessonService.deleteLesson(id);
//    return String.format("redirect:/course/%d", courseId);
//  }
}
