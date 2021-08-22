package com.alekseenko.lms.dto;

import com.alekseenko.lms.domain.Lesson;
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


  public LessonDto(Lesson lesson) {
    this.id = lesson.getId();
    this.title = lesson.getTitle();
    this.moduleId = lesson.getModule().getId();
  }

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

