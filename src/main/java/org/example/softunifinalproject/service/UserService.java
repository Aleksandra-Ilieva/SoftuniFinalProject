package org.example.softunifinalproject.service;

import org.example.softunifinalproject.model.dto.profileDto.ConsultationMessageDto;
import org.example.softunifinalproject.model.dto.profileDto.UserConsultationDto;
import org.example.softunifinalproject.model.dto.UserRegisterDto;
import org.example.softunifinalproject.model.dto.ViewAllUsersDto;

import java.security.Principal;

public interface UserService {
    void register(UserRegisterDto registerDto);

    ViewAllUsersDto getAllUsers();

    UserConsultationDto getUserProfile(Principal principal);

    ConsultationMessageDto cancelUserConsultation(Long id);
}
