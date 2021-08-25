package com.alekseenko.lms.domain;

import java.util.Objects;
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
import javax.persistence.Table;
import javax.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseEntity<String> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String username;

  @Column
  private String password;

  @Column
  private String firstName;

  @Column
  private String lastName;

  @Column(unique = true)
  @Email
  private String email;

  @Column
  private String phone;

  @Column
  private String socialNetworkLink;

  @Column
  private String achievements;

  @ManyToMany(mappedBy = "users")
  private Set<Course> courses;

  @ManyToMany
  private Set<Role> roles;

  @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
  private AvatarImage avatarImage;

  @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
  private Set<News> news;

  @OneToMany(mappedBy = "user")
  private Set<CourseRating> courseRatings;

  @Column
  private boolean enabled = false;

  public User() {
  }

  public User(Long id) {
    this.id = id;
  }

  public User(String username) {
    this.username = username;
  }

  public User(Long id, AvatarImage avatarImage) {
    this.id = id;
    this.avatarImage = avatarImage;
  }

  public User(Long id, String username, String password, Set<Role> roles) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.roles = roles;
  }

  public User(Long id, String username, String password, String firstName, String lastName,
      String email, String phone, String socialNetworkLink, String achievements,
      Set<Course> courses, Set<Role> roles, AvatarImage avatarImage,
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
    this.avatarImage = avatarImage;
    this.news = news;
    this.courseRatings = courseRatings;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return id.equals(user.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
