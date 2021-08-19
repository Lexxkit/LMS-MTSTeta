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

@Entity
@Table(name = "users")
public class User extends BaseEntity {

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

  // TODO: 19.08.2021 Не понимаю, нужно ли прописывать эти поля или unidirectional связи из BaseEntity достаточно
  @OneToMany(mappedBy = "createdByUser", cascade = CascadeType.REMOVE)
  private Set<Course> createdCourses;

  @OneToMany(mappedBy = "updatedByUser", cascade = CascadeType.REMOVE)
  private Set<Course> updatedCourses;

  @OneToMany(mappedBy = "createdByUser", cascade = CascadeType.REMOVE)
  private Set<Module> createdModules;

  @OneToMany(mappedBy = "updatedByUser", cascade = CascadeType.REMOVE)
  private Set<Module> updatedModules;

  @OneToMany(mappedBy = "createdByUser", cascade = CascadeType.REMOVE)
  private Set<Lesson> createdLessons;

  @OneToMany(mappedBy = "updatedByUser", cascade = CascadeType.REMOVE)
  private Set<Lesson> updatedLessons;

  @OneToMany(mappedBy = "createdByUser", cascade = CascadeType.REMOVE)
  private Set<News> createdNews;

  @OneToMany(mappedBy = "updatedByUser", cascade = CascadeType.REMOVE)
  private Set<News> updatedNews;

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
      String email, String phone, String socialNetworkLink,
      Set<Course> createdCourses, Set<Course> updatedCourses,
      Set<Module> createdModules, Set<Module> updatedModules,
      Set<Lesson> createdLessons, Set<Lesson> updatedLessons,
      Set<News> createdNews, Set<News> updatedNews,
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
    this.createdCourses = createdCourses;
    this.updatedCourses = updatedCourses;
    this.createdModules = createdModules;
    this.updatedModules = updatedModules;
    this.createdLessons = createdLessons;
    this.updatedLessons = updatedLessons;
    this.createdNews = createdNews;
    this.updatedNews = updatedNews;
    this.courses = courses;
    this.roles = roles;
    this.avatarImage = avatarImage;
    this.news = news;
    this.courseRatings = courseRatings;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getSocialNetworkLink() {
    return socialNetworkLink;
  }

  public void setSocialNetworkLink(String socialNetworkLink) {
    this.socialNetworkLink = socialNetworkLink;
  }

  public Set<Course> getCreatedCourses() {
    return createdCourses;
  }

  public void setCreatedCourses(Set<Course> createdCourses) {
    this.createdCourses = createdCourses;
  }

  public Set<Course> getUpdatedCourses() {
    return updatedCourses;
  }

  public void setUpdatedCourses(Set<Course> updatedCourses) {
    this.updatedCourses = updatedCourses;
  }

  public Set<Module> getCreatedModules() {
    return createdModules;
  }

  public void setCreatedModules(Set<Module> createdModules) {
    this.createdModules = createdModules;
  }

  public Set<Module> getUpdatedModules() {
    return updatedModules;
  }

  public void setUpdatedModules(Set<Module> updatedModules) {
    this.updatedModules = updatedModules;
  }

  public Set<Lesson> getCreatedLessons() {
    return createdLessons;
  }

  public void setCreatedLessons(Set<Lesson> createdLessons) {
    this.createdLessons = createdLessons;
  }

  public Set<Lesson> getUpdatedLessons() {
    return updatedLessons;
  }

  public void setUpdatedLessons(Set<Lesson> updatedLessons) {
    this.updatedLessons = updatedLessons;
  }

  public Set<News> getCreatedNews() {
    return createdNews;
  }

  public void setCreatedNews(Set<News> createdNews) {
    this.createdNews = createdNews;
  }

  public Set<News> getUpdatedNews() {
    return updatedNews;
  }

  public void setUpdatedNews(Set<News> updatedNews) {
    this.updatedNews = updatedNews;
  }

  public Set<Course> getCourses() {
    return courses;
  }

  public void setCourses(Set<Course> courses) {
    this.courses = courses;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  public AvatarImage getAvatarImage() {
    return avatarImage;
  }

  public void setAvatarImage(AvatarImage avatarImage) {
    this.avatarImage = avatarImage;
  }

  public Set<News> getNews() {
    return news;
  }

  public void setNews(Set<News> news) {
    this.news = news;
  }

  public Set<CourseRating> getCourseRatings() {
    return courseRatings;
  }

  public void setCourseRatings(Set<CourseRating> courseRatings) {
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
