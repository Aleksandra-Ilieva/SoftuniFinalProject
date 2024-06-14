package org.example.softunifinalproject.service;

import org.example.softunifinalproject.model.dto.UserRegisterDto;
import org.example.softunifinalproject.model.dto.ViewAllUsersDto;

public interface UserService {
    void register(UserRegisterDto registerDto);

    ViewAllUsersDto getAllUsers();
}
