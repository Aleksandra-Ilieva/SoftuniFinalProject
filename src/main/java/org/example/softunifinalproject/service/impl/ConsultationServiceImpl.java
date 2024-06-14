package org.example.softunifinalproject.service.impl;

import org.example.softunifinalproject.model.dto.ConsultationDto;
import org.example.softunifinalproject.model.entity.Consultation;
import org.example.softunifinalproject.model.entity.User;
import org.example.softunifinalproject.repository.ConsultationRepository;
import org.example.softunifinalproject.repository.UserRepository;
import org.example.softunifinalproject.service.ConsultationService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConsultationServiceImpl implements ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public ConsultationServiceImpl(ConsultationRepository consultationRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.consultationRepository = consultationRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public boolean saveAppointment(ConsultationDto consultationDto) {

        User user = this.userRepository.findUserByEmailAndUsername(consultationDto.getEmail(), consultationDto.getUsername());
        if (user == null) {
            return false;
        }
        Consultation consultation = modelMapper.map(consultationDto, Consultation.class);
        LocalDateTime dateTime = consultationDto.getDate().atTime(consultationDto.getTime());
        consultation.setDateTime(dateTime);
        consultation.setUser(user);
        consultationRepository.save(consultation);

        return true;


    }
}
