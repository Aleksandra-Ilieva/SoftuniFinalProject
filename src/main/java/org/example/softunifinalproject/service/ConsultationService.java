package org.example.softunifinalproject.service;

import org.example.softunifinalproject.model.dto.doctorPageDto.AllNewConsultationsDto;
import org.example.softunifinalproject.model.dto.doctorPageDto.ApprovedConsultationsDto;
import org.example.softunifinalproject.model.dto.ConsultationDto;

import java.security.Principal;

public interface ConsultationService {
    boolean saveAppointment(ConsultationDto consultationDto, Principal principal);

    AllNewConsultationsDto getAllConsultations();

    void cancelConsultation(long id);

    void approve(long id);

    ApprovedConsultationsDto getAllApprovedConsultations();

    void setAsConsulted(long id);
}
