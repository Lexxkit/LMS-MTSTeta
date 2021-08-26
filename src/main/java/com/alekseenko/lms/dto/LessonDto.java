package com.alekseenko.lms.dto;

import com.alekseenko.lms.validator.type.TitleCase;
import com.alekseenko.lms.validator.type.TitleType;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LessonDto {

  private Long id;

  @NotBlank(message = "Title shouldn't be empty")
  @TitleCase(type = TitleType.ANY)
  private String title;

  private String content;

  private Long moduleId;

  public LessonDto(Long moduleId) {
    this.moduleId = moduleId;
  }

  public LessonDto(Long id, String title, Long moduleId) {
    this.id = id;
    this.title = title;
    this.moduleId = moduleId;
  }

}
