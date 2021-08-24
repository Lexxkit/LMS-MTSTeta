package com.alekseenko.lms.dto;

import com.alekseenko.lms.domain.Module;
import com.alekseenko.lms.validator.type.TitleCase;
import com.alekseenko.lms.validator.type.TitleType;
import javax.validation.constraints.NotBlank;

public class ModuleDto {

  private Long id;

  @NotBlank(message = "Title shouldn't be empty")
  @TitleCase(type = TitleType.ANY)
  private String title;

  private String content;

  private Long courseId;

  public ModuleDto() {
  }

  public ModuleDto(Long courseId) {
    this.courseId = courseId;
  }

  public ModuleDto(Module module) {
    this.id = module.getId();
    this.title = module.getTitle();
    this.courseId = module.getCourse().getId();
  }

  public ModuleDto(Long id, String title, Long courseId) {
    this.id = id;
    this.title = title;
    this.courseId = courseId;
  }

  public ModuleDto(Long id, String title, String content, Long courseId) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.courseId = courseId;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Long getCourseId() {
    return courseId;
  }

  public void setCourseId(Long courseId) {
    this.courseId = courseId;
  }
}

