package com.alekseenko.lms.dto;

import com.alekseenko.lms.domain.User;

import java.util.Set;

public class RoleDto {

    private Long id;

    private String name;

    private Set<User> users;

    public RoleDto(String name) {
        this.name = name;
    }

    public RoleDto(Long id, String name, Set<User> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
