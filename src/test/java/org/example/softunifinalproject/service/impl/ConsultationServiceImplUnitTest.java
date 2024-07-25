package org.example.softunifinalproject.service.impl;

import org.example.softunifinalproject.model.dto.ConsultationDto;
import org.example.softunifinalproject.model.entity.Consultation;
import org.example.softunifinalproject.model.entity.User;
import org.example.softunifinalproject.model.enums.EmailMessage;
import org.example.softunifinalproject.repository.ConsultationRepository;
import org.example.softunifinalproject.repository.UserRepository;
import org.example.softunifinalproject.service.EmailSenderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ConsultationServiceImplUnitTest {

    @Mock
    private ConsultationRepository consultationRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailSenderService emailSenderService;

    @InjectMocks
    private ConsultationServiceImpl consultationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveAppointment_success() {
        ConsultationDto consultationDto = new ConsultationDto();
        consultationDto.setDate(LocalDate.now());
        consultationDto.setTime(LocalDateTime.now().toLocalTime());

        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("user@example.com");

        User user = new User();
        user.setConsultations(new HashSet<>() {
        });

        when(userRepository.findUserByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(modelMapper.map(consultationDto, Consultation.class)).thenReturn(new Consultation());

        boolean result = consultationService.saveAppointment(consultationDto, principal);

        assertTrue(result);
        verify(consultationRepository, times(1)).save(any(Consultation.class));
    }

    @Test
    void testSaveAppointment_tooManyAppointments() {
        ConsultationDto consultationDto = new ConsultationDto();
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("user@example.com");

        User user = new User();
        Set<Consultation> consultations = new HashSet<>();
        consultations.add(new Consultation());
        consultations.add(new Consultation());
        consultations.add(new Consultation());
        user.setConsultations(consultations);

        when(userRepository.findUserByEmail("user@example.com")).thenReturn(Optional.of(user));

        boolean result = consultationService.saveAppointment(consultationDto, principal);

        assertFalse(result);
        verify(consultationRepository, never()).save(any(Consultation.class));
    }



    @Test
    void testCancelConsultation() {
        Consultation consultation = new Consultation();
        consultation.setDateTime(LocalDateTime.now().plusDays(1));
        User user = new User();
        user.setEmail("user@example.com");
        consultation.setUser(user);

        when(consultationRepository.findById(1L)).thenReturn(Optional.of(consultation));

        consultationService.cancelConsultation(1L);

        verify(emailSenderService, times(1)).sendSimpleMessage(eq("user@example.com"), eq(EmailMessage.REFUSED_CONSULTATION.getSubject()), anyString());
        verify(consultationRepository, times(1)).deleteById(1L);
    }

    @Test
    void testApprove() {
        Consultation consultation = new Consultation();
        consultation.setDateTime(LocalDateTime.now().plusDays(1));
        User user = new User();
        user.setEmail("user@example.com");
        consultation.setUser(user);

        when(consultationRepository.findById(1L)).thenReturn(Optional.of(consultation));

        consultationService.approve(1L);

        assertTrue(consultation.getAccepted());
        verify(emailSenderService, times(1)).sendSimpleMessage(eq("user@example.com"), eq(EmailMessage.APPROVED_CONSULTATION.getSubject()), anyString());
        verify(consultationRepository, times(1)).save(consultation);
    }



    @Test
    void testSetAsConsulted() {
        Consultation consultation = new Consultation();

        when(consultationRepository.findById(1L)).thenReturn(Optional.of(consultation));

        consultationService.setAsConsulted(1L);

        assertTrue(consultation.getConsulted());
        verify(consultationRepository, times(1)).save(consultation);
    }



    @Test
    void testCheckIfAlreadyBooked_false() {
        List<Consultation> consultations = new ArrayList<>();
        when(consultationRepository.findAll()).thenReturn(consultations);

        ConsultationDto consultationDto = new ConsultationDto();
        consultationDto.setDate(LocalDate.now().plusDays(1));
        consultationDto.setTime(LocalDateTime.now().toLocalTime());

        boolean result = consultationService.checkIfAlreadyBooked(consultationDto);

        assertFalse(result);
    }

    
}

