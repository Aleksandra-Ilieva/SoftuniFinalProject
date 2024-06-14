package org.example.softunifinalproject.model.dto;

import org.example.softunifinalproject.model.enums.RoleType;

import java.util.ArrayList;
import java.util.List;

public class UserDto {
    private  long id;
    private String username;
    private String email;
    private List<String> roleNames;

    public UserDto() {
        roleNames = new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(List<String> roleNames) {
        this.roleNames = roleNames;
    }
}
