package com.alekseenko.lms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "lessons")
public class Lesson extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @NotBlank(message = "Lesson title has to be filled")
  @Column
  private String title;

  @Lob
  @Column
  private String description;

  @NotBlank(message = "Lesson test has to be filled")
  @Lob
  @Column
  private String content;

  @ManyToOne(optional = false)
  private Module module;

  public Lesson() {
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

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Module getModule() {
    return module;
  }

  public void setModule(Module module) {
    this.module = module;
  }
}
