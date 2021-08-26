package com.alekseenko.lms.dto;

import com.alekseenko.lms.domain.CourseImage;
import com.alekseenko.lms.domain.CourseRating;
import com.alekseenko.lms.domain.Module;
import com.alekseenko.lms.domain.User;
import com.alekseenko.lms.validator.type.TitleCase;
import com.alekseenko.lms.validator.type.TitleType;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CourseDto {

  private Long id;

  @NotBlank(message = "Course author has to be filled")
  private String author;

  @NotBlank(message = "Course title has to be filled")
  @TitleCase(type = TitleType.ANY)
  private String title;

  private String description;

  private Integer durationWeeks;

  private String tag;

  private Double avgRating;

  private List<Module> modules;

  private Set<User> users;

  private CourseImage courseImage;

  private Set<CourseRating> courseRatings;

  private String createdAt;

  public CourseDto(Long id, String author, String title, CourseImage courseImage) {
    this.id = id;
    this.author = author;
    this.title = title;
    this.courseImage = courseImage;
  }

  public CourseDto(Long id, String author, String title, Set<User> users) {
    this.id = id;
    this.author = author;
    this.title = title;
    this.users = users;
  }

}
