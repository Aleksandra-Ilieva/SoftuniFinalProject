package org.example.softunifinalproject.service.impl;

import org.example.softunifinalproject.model.dto.doctorPageDto.AllNewConsultationsDto;
import org.example.softunifinalproject.model.dto.doctorPageDto.ApprovedConsultationsDto;
import org.example.softunifinalproject.model.dto.ConsultationDto;
import org.example.softunifinalproject.model.dto.profileDto.BusySlotsDto;
import org.example.softunifinalproject.model.entity.Consultation;
import org.example.softunifinalproject.model.entity.User;
import org.example.softunifinalproject.model.enums.EmailMessage;
import org.example.softunifinalproject.repository.ConsultationRepository;
import org.example.softunifinalproject.repository.UserRepository;
import org.example.softunifinalproject.service.ConsultationService;
import org.example.softunifinalproject.service.EmailSenderService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConsultationServiceImpl implements ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final EmailSenderService emailSenderService;

    public ConsultationServiceImpl(ConsultationRepository consultationRepository, ModelMapper modelMapper, UserRepository userRepository, EmailSenderService emailSenderService) {
        this.consultationRepository = consultationRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.emailSenderService = emailSenderService;
    }

    @Override
    public boolean saveAppointment(ConsultationDto consultationDto, Principal principal) {
        Optional<User> user = this.userRepository.findUserByEmail(principal.getName());
        int currentCountOfAppointments = 0;
        for (Consultation consultation : user.get().getConsultations()) {
            if (consultation.getConsulted() == null) {
                currentCountOfAppointments++;
            }

        }
        if (currentCountOfAppointments > 2) {
            return false;
        }
        Consultation consultation = modelMapper.map(consultationDto, Consultation.class);
        LocalDateTime dateTime = consultationDto.getDate().atTime(consultationDto.getTime());
        consultation.setDateTime(dateTime);
        consultation.setUser(user.get());
        consultationRepository.save(consultation);

        return true;


    }



    @Override
    public AllNewConsultationsDto getAllConsultations() {
        List<Consultation> consultations = this.consultationRepository.findAll();
        List<ConsultationDto> consultationDtos = new ArrayList<>();
        for (Consultation consultation : consultations) {
            if (consultation.getAccepted() == null || !consultation.getAccepted().equals(true)) {
                ConsultationDto dto = mapToConsultationDto(consultation, consultationDtos);
                dto.setEmail(consultation.getUser().getEmail());
                dto.setUsername(consultation.getUser().getUsername());
                consultationDtos.add(dto);
            }

        }
        AllNewConsultationsDto allConsultationsView = new AllNewConsultationsDto();
        allConsultationsView.setConsultationDtoList(consultationDtos);
        return allConsultationsView;
    }

    private static ConsultationDto mapToConsultationDto(Consultation consultation, List<ConsultationDto> consultationDtos) {
        ConsultationDto consultationDto = new ConsultationDto();
        consultationDto.setId(consultation.getId());
        consultationDto.setDate(consultation.getDateTime().toLocalDate());
        consultationDto.setTime(consultation.getDateTime().toLocalTime());

        return consultationDto;
    }

    @Override
    public void cancelConsultation(long id) {
        Consultation consultation = this.consultationRepository.findById(id).get();
        String date = consultation.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.emailSenderService.sendSimpleMessage(consultation.getUser().getEmail(), EmailMessage.REFUSED_CONSULTATION.getSubject(), String.format(EmailMessage.REFUSED_CONSULTATION.getText(date)));
        this.consultationRepository.deleteById(id);
    }

    @Override
    public void approve(long id) {
        Consultation consultation = this.consultationRepository.findById(id).get();
        consultation.setAccepted(true);

        String date = consultation.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.emailSenderService.sendSimpleMessage(consultation.getUser().getEmail(), EmailMessage.APPROVED_CONSULTATION.getSubject(), String.format(EmailMessage.APPROVED_CONSULTATION.getText(date)));
        this.consultationRepository.save(consultation);
    }

    @Override
    public ApprovedConsultationsDto getAllApprovedConsultations() {
        List<Consultation> consultations = this.consultationRepository.findAll();
        List<ConsultationDto> consultationDtos = new ArrayList<>();
        for (Consultation consultation : consultations) {
            if (consultation.getAccepted() != null && consultation.getAccepted().equals(true) && (consultation.getConsulted() == null || !consultation.getConsulted())) {
                ConsultationDto dto = mapToConsultationDto(consultation, consultationDtos);
                dto.setEmail(consultation.getUser().getEmail());
                dto.setUsername(consultation.getUser().getUsername());
                consultationDtos.add(dto);
            }

        }
        ApprovedConsultationsDto approvedConsultationsDto = new ApprovedConsultationsDto();
        approvedConsultationsDto.setConsultations(consultationDtos);

        return approvedConsultationsDto;
    }

    @Override
    public void setAsConsulted(long id) {
        Optional<Consultation> consultation = this.consultationRepository.findById(id);
        consultation.get().setConsulted(true);
        this.consultationRepository.save(consultation.get());

    }

    @Override
    public BusySlotsDto findBusySlots() {
        List<LocalDateTime> busy = new ArrayList<>();

     List<Consultation> consultations =   this.consultationRepository.findAll();
        for (Consultation consultation : consultations) {
            LocalDateTime localDateTime = consultation.getDateTime();
            if(LocalDate.now().plusDays(5).equals(consultation.getDateTime().toLocalDate())){
                break;
            }
            if(consultation.getAccepted() !=null && consultation.getConsulted()==null && consultation.getDateTime().isAfter(LocalDateTime.now())){
                busy.add(localDateTime);
            }

        }


        BusySlotsDto busySlotsDto = new BusySlotsDto();
        busySlotsDto.setBusySlots(busy);
        return  busySlotsDto;
    }

    @Override
    public boolean checkIfAlreadyBooked(ConsultationDto consultationDto) {
        List<Consultation> consultations = this.consultationRepository.findAll();
        LocalDateTime appointmentLocalDateTime = consultationDto.getDate().atTime(consultationDto.getTime());
        for (Consultation consultation : consultations) {  LocalDateTime endInterval = consultation.getDateTime().plusMinutes(30);
            if (appointmentLocalDateTime.isEqual(consultation.getDateTime()) || // ако appointmentLocalDateTime е точно равен на consultationDateTime
                    (appointmentLocalDateTime.isAfter(consultation.getDateTime()) && appointmentLocalDateTime.isBefore(endInterval))) { // или ако appointmentLocalDateTime е в интервала между consultationDateTime и endInterval
                return true;
            }
        }
        return false;
    }

}
