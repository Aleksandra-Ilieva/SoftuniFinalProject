package org.example.softunifinalproject.service.impl;

import org.example.softunifinalproject.model.dto.ConsultationDto;
import org.example.softunifinalproject.model.dto.profileDto.ConsultationMessageDto;
import org.example.softunifinalproject.model.dto.profileDto.UserConsultationDto;
import org.example.softunifinalproject.model.dto.UserDto;
import org.example.softunifinalproject.model.dto.UserRegisterDto;
import org.example.softunifinalproject.model.dto.ViewAllUsersDto;
import org.example.softunifinalproject.model.entity.Consultation;
import org.example.softunifinalproject.model.entity.Role;
import org.example.softunifinalproject.model.entity.User;
import org.example.softunifinalproject.repository.ConsultationRepository;
import org.example.softunifinalproject.repository.UserRepository;
import org.example.softunifinalproject.service.RoleService;
import org.example.softunifinalproject.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final ConsultationRepository consultationRepository;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, RoleService roleService, ConsultationRepository consultationRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.consultationRepository = consultationRepository;
    }

    @Override
    public void register(UserRegisterDto registerDto) {
        User user = this.modelMapper.map(registerDto, User.class);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        Role role = roleService.getRoleForNormalUser();
        user.getRoles().add(role);
        this.userRepository.save(user);

    }

    @Override
    public ViewAllUsersDto getAllUsers() {
        List<User> dtoList = this.userRepository.findAll();
        ViewAllUsersDto viewAllUsersDto = new ViewAllUsersDto();
        for (User user : dtoList) {
            List<Role> roles = user.getRoles();
            UserDto userDto = this.modelMapper.map(user, UserDto.class);
            for (Role role : roles) {
                userDto.getRoleNames().add(role.getRoleType().name());

            }

            viewAllUsersDto.getUsers().add(userDto);

        }
        return viewAllUsersDto;
    }

    @Override
    public UserConsultationDto getUserProfile(Principal principal) {
        UserConsultationDto userConsultationDto = new UserConsultationDto();
        Optional<User> user = this.userRepository.findUserByEmail(principal.getName());

        Set<Consultation> consultations = user.get().getConsultations();
        List<ConsultationDto> consultationDtos = new ArrayList<>();
        for (Consultation consultation : consultations) {
            if (consultation.getConsulted() == null && consultation.getAccepted() != null) {
                ConsultationDto dto = new ConsultationDto();
                dto.setDate(consultation.getDateTime().toLocalDate());
                dto.setTime(consultation.getDateTime().toLocalTime());
                dto.setId(consultation.getId());
                consultationDtos.add(dto);
            }

        }
        userConsultationDto.setUsername(user.get().getUsername());
        userConsultationDto.setUserConsultations(consultationDtos);
        return userConsultationDto;
    }

    @Override
    public ConsultationMessageDto cancelUserConsultation(Long id) {
        Optional<Consultation> consultation = this.consultationRepository.findById(id);
        ConsultationMessageDto messageDto = new ConsultationMessageDto();
        if (LocalDateTime.now().plusHours(2).isAfter(consultation.get().getDateTime())) {
            messageDto.setCanceled(false);
            return messageDto;
        }
        messageDto.setCanceled(true);
        this.consultationRepository.delete(consultation.get());
        return messageDto;
    }
}
