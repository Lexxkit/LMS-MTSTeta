package com.alekseenko.lms.domain;

import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "courses")
public class Course extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column
  private String author;

  @Column
  private String title;

  @Column
  @Lob
  private String description;

  @Column
  private Integer durationWeeks;

  @Column
  private String tag;

  @Column
  private Double avgRating;

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

  public Course(Long id, String author, String title, String description,
      Integer durationWeeks, String tag, Double avgRating,
      List<Lesson> lessons, Set<User> users, CourseImage courseImage) {
    this.id = id;
    this.author = author;
    this.title = title;
    this.description = description;
    this.durationWeeks = durationWeeks;
    this.tag = tag;
    this.avgRating = avgRating;
    this.lessons = lessons;
    this.users = users;
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
