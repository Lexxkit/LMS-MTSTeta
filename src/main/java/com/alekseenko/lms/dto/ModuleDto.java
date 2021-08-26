package com.alekseenko.lms.dto;

import com.alekseenko.lms.domain.Module;
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
public class ModuleDto {

  private Long id;

  @NotBlank(message = "Title shouldn't be empty")
  @TitleCase(type = TitleType.ANY)
  private String title;

  private String content;

  private Long courseId;

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

}
