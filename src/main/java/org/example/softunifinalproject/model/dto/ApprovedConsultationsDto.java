package org.example.softunifinalproject.model.dto;

import java.util.List;

public class ApprovedConsultationsDto {
    private List<ConsultationDto> consultations;

    public List<ConsultationDto> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<ConsultationDto> consultations) {
        this.consultations = consultations;
    }
}
