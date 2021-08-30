package com.alekseenko.lms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "lessons")
@NoArgsConstructor
@Getter
@Setter
public class Lesson extends BaseEntity<String> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column
  private String title;

  @Column
  private String description;

  @Column
  private String content;

  @Column
  private Long courseId;

  @ManyToOne(optional = false)
  private Module module;

  public Lesson(Long id, String title, String content, Module module, Long courseId) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.module = module;
    this.courseId = courseId;
  }

  public Lesson( String title, String content, Module module) {
    this.title = title;
    this.content = content;
    this.module = module;
  }

  public Lesson(Long id, String title, String content, Module module) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.module = module;
  }

  public Lesson(Long id, String title, String description, String content,
      Module module) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.content = content;
    this.module = module;
  }

}
