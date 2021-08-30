package com.alekseenko.lms.domain;

import com.alekseenko.lms.dto.ModuleDto;
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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "modules")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Module extends BaseEntity<String> {

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

  @OneToMany(mappedBy = "module", cascade = CascadeType.REMOVE)
  @OrderBy("id")
  private List<Lesson> lessons;

  public Module(ModuleDto moduleDto) {
    this.id = moduleDto.getId();
    this.title = moduleDto.getTitle();
    this.description = moduleDto.getContent();
  }


  public Module(ModuleDto moduleDto, Course course) {
//    this.id = moduleDto.getId();
    this.title = moduleDto.getTitle();
    this.description = moduleDto.getContent();
    this.course = course;
  }
}
