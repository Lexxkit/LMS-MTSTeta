package com.alekseenko.lms.dto;

import com.alekseenko.lms.domain.Course;
import com.alekseenko.lms.domain.CourseRating;
import com.alekseenko.lms.domain.News;
import com.alekseenko.lms.domain.Role;
import com.alekseenko.lms.validator.type.TitleCase;
import com.alekseenko.lms.validator.type.TitleType;
import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {

  private Long id;

  @NotBlank(message = "Логин не должен быть пустым")
  @TitleCase(type = TitleType.LOGIN, message = "Поле Логин должно содержать только латиницу и/или спец.символы")
  private String username;

  @Size(min = 8, message = "Пароль должен быть не менее 8 символов")
  @TitleCase(type = TitleType.PASSWORD, message = "Некорректный формат пароля")
  @NotBlank(message = "Пароль не должен быть пустым")
  private String password;

  private String firstName;

  private String lastName;

  @Email
  @TitleCase(type = TitleType.EMAIL, message = "Некорректный формат email")
  @NotBlank(message = "Email не должен быть пустым")
  private String email;

  @TitleCase(type = TitleType.PHONE, message = "Некорректный формат телефона")
  private String phone;

  private String socialNetworkLink;

  private String achievements;

  private Set<Course> courses;

  private Set<Role> roles;

  private Set<News> news;

  private Set<CourseRating> courseRatings;

  public UserDto(String username) {
    this.username = username;
  }

  public UserDto(Long id, String username, String password, Set<Role> roles) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.roles = roles;
  }

}
