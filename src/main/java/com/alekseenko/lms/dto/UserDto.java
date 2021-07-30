package com.alekseenko.lms.dto;

import com.alekseenko.lms.domain.Course;
import com.alekseenko.lms.domain.Role;

import javax.validation.constraints.NotBlank;
import java.util.Set;

public class UserDto {

    private Long id;

    @NotBlank(message = "Username shouldn't be empty")
    private String username;

    @NotBlank(message = "User password shouldn't be empty")
    private String password;

    private Set<Course> courses;

    private Set<Role> roles;

    public UserDto() {
    }

    public UserDto(String username) {
        this.username = username;
    }

    public UserDto(Long id, String username, String password, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
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
}
