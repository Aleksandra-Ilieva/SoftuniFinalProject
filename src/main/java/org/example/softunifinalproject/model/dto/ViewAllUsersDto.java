package org.example.softunifinalproject.model.dto;

import java.util.ArrayList;
import java.util.List;

public class ViewAllUsersDto {

    private List<UserDto> users;

    public ViewAllUsersDto() {
        this.users = new ArrayList<>();
    }

    public List<UserDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserDto> users) {
        this.users = users;
    }
}
