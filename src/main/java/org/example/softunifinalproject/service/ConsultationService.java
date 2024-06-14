package org.example.softunifinalproject.service;

import org.example.softunifinalproject.model.dto.ConsultationDto;

public interface ConsultationService {
    boolean saveAppointment(ConsultationDto consultationDto);
}
