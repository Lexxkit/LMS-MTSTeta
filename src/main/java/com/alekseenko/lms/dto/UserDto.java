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

  @Size(min = 10, max = 10, message = "Некорректный формат телефона, только 10 цифр")
  private String phone;

  private String socialNetworkLink;

  private String achievements;

  private Set<Course> courses;

  private Set<Role> roles;

  private Set<News> news;

  private Set<CourseRating> courseRatings;

  public UserDto() {
  }

  public UserDto(String username) {
    this.username = username;
  }

  public UserDto(Long id, String username, String password, Set<Role> roles) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.roles = roles;
  }

  public UserDto(Long id, String username, String password, String firstName,
      String lastName, String email, String phone, String socialNetworkLink,
      String achievements, Set<Course> courses, Set<Role> roles,
      Set<News> news, Set<CourseRating> courseRatings) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phone = phone;
    this.socialNetworkLink = socialNetworkLink;
    this.achievements = achievements;
    this.courses = courses;
    this.roles = roles;
    this.news = news;
    this.courseRatings = courseRatings;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getSocialNetworkLink() {
    return socialNetworkLink;
  }

  public void setSocialNetworkLink(String socialNetworkLink) {
    this.socialNetworkLink = socialNetworkLink;
  }

  public String getAchievements() {
    return achievements;
  }

  public void setAchievements(String achievements) {
    this.achievements = achievements;
  }

  public Set<Course> getCourses() {
    return courses;
  }

  public void setCourses(Set<Course> courses) {
    this.courses = courses;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  public Set<News> getNews() {
    return news;
  }

  public void setNews(Set<News> news) {
    this.news = news;
  }

  public Set<CourseRating> getCourseRatings() {
    return courseRatings;
  }

  public void setCourseRatings(Set<CourseRating> courseRatings) {
    this.courseRatings = courseRatings;
  }
}
