package org.example.softunifinalproject.service;

import org.example.softunifinalproject.model.dto.userProfileDto.ConsultationMessageDto;
import org.example.softunifinalproject.model.dto.userProfileDto.UserConsultationDto;
import org.example.softunifinalproject.model.dto.UserRegisterDto;
import org.example.softunifinalproject.model.dto.adminPageDto.ViewAllUsersDto;

import java.security.Principal;

public interface UserService {
    void register(UserRegisterDto registerDto);

    ViewAllUsersDto getAllUsers();

    UserConsultationDto getUserProfile(Principal principal);

    ConsultationMessageDto cancelUserConsultation(Long id);
}
