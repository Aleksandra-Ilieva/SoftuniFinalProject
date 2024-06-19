package org.example.softunifinalproject.service;

import org.example.softunifinalproject.model.dto.AllConsultationsView;
import org.example.softunifinalproject.model.dto.ConsultationDto;

import java.util.List;

public interface ConsultationService {
    boolean saveAppointment(ConsultationDto consultationDto);

    AllConsultationsView getAllConsultations();
}
