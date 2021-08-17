package com.alekseenko.lms.dto;

import com.alekseenko.lms.domain.CourseImage;
import com.alekseenko.lms.domain.Lesson;
import com.alekseenko.lms.domain.User;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.NotBlank;

public class CourseDto {

  private Long id;

  @NotBlank(message = "Course author has to be filled")
  private String author;

  @NotBlank(message = "Course title has to be filled")
  private String title;

  private List<Lesson> lessons;

  private Set<User> users;

  private CourseImage courseImage;

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

  public List<Lesson> getLessons() {
    return lessons;
  }

  public void setLessons(List<Lesson> lessons) {
    this.lessons = lessons;
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
}
