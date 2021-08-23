package com.alekseenko.lms.dto;

import javax.validation.constraints.NotBlank;

public class LessonDto {

  private Long id;

  @NotBlank(message = "Title shouldn't be empty")
  private String title;

  @NotBlank(message = "Text shouldn't be empty")
  private String content;

  private Long moduleId;

  public LessonDto() {
  }

//  public LessonDto(Long id, Long moduleId) {
//    this.id = id;
//    this.moduleId = moduleId;
//  }

  public LessonDto(Long moduleId) {
    this.moduleId = moduleId;
  }

  public LessonDto(Long id, String title, Long moduleId) {
    this.id = id;
    this.title = title;
    this.moduleId = moduleId;
  }

  public LessonDto(Long id, String title, String content, Long moduleId) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.moduleId = moduleId;
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

  public Long getModuleId() {
    return moduleId;
  }

  public void setModuleId(Long moduleId) {
    this.moduleId = moduleId;
  }
}

