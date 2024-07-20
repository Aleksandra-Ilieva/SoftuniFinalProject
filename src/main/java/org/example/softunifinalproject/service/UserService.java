package org.example.softunifinalproject.service;

import org.example.softunifinalproject.model.dto.UserDto;
import org.example.softunifinalproject.model.dto.userProfileDto.ConsultationMessageDto;
import org.example.softunifinalproject.model.dto.userProfileDto.UserConsultationDto;
import org.example.softunifinalproject.model.dto.UserRegisterDto;
import org.example.softunifinalproject.model.dto.adminPageDto.ViewAllUsersDto;

import java.security.Principal;

public interface UserService {
    boolean register(UserRegisterDto registerDto);

    ViewAllUsersDto getAllUsers();

    UserConsultationDto getUserProfile(Principal principal);

    ConsultationMessageDto cancelUserConsultation(Long id);

    UserDto getUser(long id);

    boolean validateEmail(String email);

    boolean validateUsername(String username);
}
