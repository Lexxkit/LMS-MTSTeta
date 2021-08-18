package com.alekseenko.lms.domain;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "modules")
public class Module extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column
  private String title;

  @Column
  private String description;

  @ManyToOne
  private Course course;

  @OneToMany(mappedBy = "module", cascade = CascadeType.REMOVE) // TODO: 18.08.2021 Уточнить тип удаления
  @OrderBy("id")
  private List<Lesson> lessons;

  public Module() {
  }

  public Module(Long id, String title, String description, Course course,
      List<Lesson> lessons) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.course = course;
    this.lessons = lessons;
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public List<Lesson> getLessons() {
    return lessons;
  }

  public void setLessons(List<Lesson> lessons) {
    this.lessons = lessons;
  }
}
