package com.alekseenko.lms.dto;

import com.alekseenko.lms.domain.CourseImage;
import com.alekseenko.lms.domain.CourseRating;
import com.alekseenko.lms.domain.Lesson;
import com.alekseenko.lms.domain.Module;
import com.alekseenko.lms.domain.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.NotBlank;

public class CourseDto {

  private Long id;

  @NotBlank(message = "Course author has to be filled")
  private String author;

  @NotBlank(message = "Course title has to be filled")
  private String title;

  private String description;

  private Integer durationWeeks;

  private String tag;

  private Double avgRating;

  private List<Module> modules;

  private Set<User> users;

  private CourseImage courseImage;

  private Set<CourseRating> courseRatings;

  private LocalDateTime createdAt;

  public CourseDto() {
  }

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

  public CourseDto(Long id, String author, String title, String description,
      Integer durationWeeks, String tag, Double avgRating,
      List<Module> modules, Set<User> users, CourseImage courseImage,
      Set<CourseRating> courseRatings, LocalDateTime createdAt) {
    this.id = id;
    this.author = author;
    this.title = title;
    this.description = description;
    this.durationWeeks = durationWeeks;
    this.tag = tag;
    this.avgRating = avgRating;
    this.modules = modules;
    this.users = users;
    this.courseImage = courseImage;
    this.courseRatings = courseRatings;
    this.createdAt = createdAt;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getDurationWeeks() {
    return durationWeeks;
  }

  public void setDurationWeeks(Integer durationWeeks) {
    this.durationWeeks = durationWeeks;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public Double getAvgRating() {
    return avgRating;
  }

  public void setAvgRating(Double avgRating) {
    this.avgRating = avgRating;
  }

  public List<Module> getModules() {
    return modules;
  }

  public void setModules(List<Module> modules) {
    this.modules = modules;
  }

  public Set<User> getUsers() {
    return users;
  }

  public void setUsers(Set<User> users) {
    this.users = users;
  }

  public CourseImage getCourseImage() {
    return courseImage;
  }

  public void setCourseImage(CourseImage courseImage) {
    this.courseImage = courseImage;
  }

  public Set<CourseRating> getCourseRatings() {
    return courseRatings;
  }

  public void setCourseRatings(Set<CourseRating> courseRatings) {
    this.courseRatings = courseRatings;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
}
