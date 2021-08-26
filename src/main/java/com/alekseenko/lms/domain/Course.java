package com.alekseenko.lms.domain;

import com.alekseenko.lms.dto.CourseDto;
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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "courses")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Course extends BaseEntity<String> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column
  private String author;

  @Column
  private String title;

  @Column(columnDefinition = "text")
  private String description;

  @Column
  private Integer durationWeeks;

  @Column
  private String tag;

  @Column
  private Double avgRating;

  @OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE) // TODO: 18.08.2021 Уточнить тип удаления 
  @OrderBy("id") 
  private List<Module> modules;

  @ManyToMany
  private Set<User> users;

  @OneToOne(mappedBy = "course", cascade = CascadeType.REMOVE)
  private CourseImage courseImage;

  @OneToMany(mappedBy = "course")
  private Set<CourseRating> courseRatings;

  public Course(CourseDto courseDto) {
    this.id = courseDto.getId();
    this.author = courseDto.getAuthor();
    this.title = courseDto.getTitle();
    this.courseImage = courseDto.getCourseImage();
  }

  public Course(Long id, String author, String title, CourseImage courseImage) {
    this.id = id;
    this.author = author;
    this.title = title;
    this.courseImage = courseImage;
  }

  public Course(Long id, String author, String title, String description,
      Integer durationWeeks, String tag, CourseImage courseImage) {
    this.id = id;
    this.author = author;
    this.title = title;
    this.description = description;
    this.durationWeeks = durationWeeks;
    this.tag = tag;
    this.courseImage = courseImage;
  }

}
