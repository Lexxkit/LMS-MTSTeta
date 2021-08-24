package com.alekseenko.lms.controller;

import com.alekseenko.lms.constants.RoleConstants;
import com.alekseenko.lms.dto.CourseDto;
import com.alekseenko.lms.dto.ModuleDto;
import com.alekseenko.lms.service.CourseService;
import com.alekseenko.lms.service.LessonService;
import com.alekseenko.lms.service.ModuleService;
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
@Secured({RoleConstants.ROLE_OWNER, RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_TUTOR})
@RequestMapping("/module")
public class ModuleController {

  private final ModuleService moduleService;
  private final LessonService lessonService;
  private final CourseService courseService;


  @Autowired
  public ModuleController(ModuleService moduleService,
      LessonService lessonService, CourseService courseService) {
    this.moduleService = moduleService;
    this.lessonService = lessonService;
    this.courseService = courseService;
  }

  @GetMapping("/new")
  public String moduleNewForm(Model model, @RequestParam("course_id") Long courseId) {
    model.addAttribute("moduleDto", new ModuleDto(courseId));
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

  @RequestMapping("/{id}")
  public String lessonForm(Model model, @PathVariable("id") Long id) {
    ModuleDto moduleDto = moduleService.findById(id);
    CourseDto currentCourse = courseService.getCourseById(moduleDto.getCourseId());
    model.addAttribute("course", currentCourse);
    model
        .addAttribute("lessons", lessonService.getAllForLessonIdWithoutText(moduleDto.getId()));
    model
        .addAttribute("moduleDto", moduleDto);
    return "lesson-table";
  }

  @DeleteMapping("/{id}")
  public String deleteLesson(@PathVariable("id") Long id,
      @RequestParam("courseId") Long courseId) {
    moduleService.deleteModule(id);
    return String.format("redirect:/course/%d", courseId);
  }
}
