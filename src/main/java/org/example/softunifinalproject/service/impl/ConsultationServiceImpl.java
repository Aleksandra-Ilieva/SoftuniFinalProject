package org.example.softunifinalproject.service.impl;

import org.example.softunifinalproject.model.dto.AllNewConsultationsDto;
import org.example.softunifinalproject.model.dto.ApprovedConsultationsDto;
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
import java.util.Optional;

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
    public AllNewConsultationsDto getAllConsultations() {
    List<Consultation> consultations=    this.consultationRepository.findAll();
    List<ConsultationDto> consultationDtos= new ArrayList<>();
    for (Consultation consultation : consultations) {
        if(consultation.getAccepted()==null || !consultation.getAccepted().equals(true)){
            ConsultationDto dto=mapToConsultatinDto(consultation, consultationDtos);
            consultationDtos.add(dto);
        }

    }
    AllNewConsultationsDto allConsultationsView = new AllNewConsultationsDto();
    allConsultationsView.setConsultationDtoList(consultationDtos);
        return allConsultationsView;
    }

    private static ConsultationDto mapToConsultatinDto(Consultation consultation, List<ConsultationDto> consultationDtos) {
        ConsultationDto consultationDto = new ConsultationDto();
        consultationDto.setId(consultation.getId());
        consultationDto.setDate(consultation.getDateTime().toLocalDate());
        consultationDto.setTime(consultation.getDateTime().toLocalTime());
        consultationDto.setEmail(consultation.getUser().getEmail());
        consultationDto.setUsername(consultation.getUser().getUsername());

        return consultationDto;
    }

    @Override
    public void cancelConsultation(long id) {
        this.consultationRepository.deleteById(id);
    }

    @Override
    public void approve(long id) {
        Consultation consultation = this.consultationRepository.findById(id).get();
        consultation.setAccepted(true);
        this.consultationRepository.save(consultation);
    }

    @Override
    public ApprovedConsultationsDto getAllApprovedConsultations() {
        List<Consultation> consultations=    this.consultationRepository.findAll();
        List<ConsultationDto> consultationDtos= new ArrayList<>();
        for (Consultation consultation : consultations) {
            if(consultation.getAccepted() != null && consultation.getAccepted().equals(true) && (consultation.getConsulted()==null || !consultation.getConsulted())){
                ConsultationDto dto =mapToConsultatinDto(consultation, consultationDtos);
                consultationDtos.add(dto);
            }

        }
        ApprovedConsultationsDto approvedConsultationsDto = new ApprovedConsultationsDto();
        approvedConsultationsDto.setConsultations(consultationDtos);

        return approvedConsultationsDto;
    }

    @Override
    public void setAsConsulted(long id) {
      Optional<Consultation> consultation=  this.consultationRepository.findById(id);
      consultation.get().setConsulted(true);
      this.consultationRepository.save(consultation.get());

    }
}
