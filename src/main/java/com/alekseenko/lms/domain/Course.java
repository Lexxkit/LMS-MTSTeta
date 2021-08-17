package com.alekseenko.lms.domain;

import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "courses")
public class Course {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column
  private String author;

  @Column
  private String title;

  @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
  @OrderBy("id")
  private List<Lesson> lessons;

  @ManyToMany
  private Set<User> users;

  @OneToOne(mappedBy = "course", cascade = CascadeType.REMOVE)
  private CourseImage courseImage;

  public Course() {
  }

  public Course(Long id, String author, String title, CourseImage courseImage) {
    this.id = id;
    this.author = author;
    this.title = title;
    this.courseImage = courseImage;
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
