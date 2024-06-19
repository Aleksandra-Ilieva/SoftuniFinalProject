package org.example.softunifinalproject.service.impl;

import org.example.softunifinalproject.model.dto.AllConsultationsView;
import org.example.softunifinalproject.model.dto.ConsultationDto;
import org.example.softunifinalproject.model.entity.Consultation;
import org.example.softunifinalproject.model.entity.User;
import org.example.softunifinalproject.repository.ConsultationRepository;
import org.example.softunifinalproject.repository.UserRepository;
import org.example.softunifinalproject.service.ConsultationService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public AllConsultationsView getAllConsultations() {
    List<Consultation> consultations=    this.consultationRepository.findAll();
    List<ConsultationDto> consultationDtos= new ArrayList<>();
    for (Consultation consultation : consultations) {
        ConsultationDto consultationDto = new ConsultationDto();
        consultationDto.setId(consultation.getId());
        consultationDto.setDate(consultation.getDateTime().toLocalDate());
        consultationDto.setTime(consultation.getDateTime().toLocalTime());
        consultationDto.setEmail(consultation.getUser().getEmail());
        consultationDto.setUsername(consultation.getUser().getUsername());
        consultationDtos.add(consultationDto);
    }
    AllConsultationsView allConsultationsView = new AllConsultationsView();
    allConsultationsView.setConsultationDtoList(consultationDtos);
        return allConsultationsView;
    }

    @Override
    public void cancelConsultation(long id) {
        this.consultationRepository.deleteById(id);
    }
}
