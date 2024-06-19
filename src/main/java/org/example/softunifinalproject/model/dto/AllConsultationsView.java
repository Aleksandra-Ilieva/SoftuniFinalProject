package org.example.softunifinalproject.model.dto;

import java.util.List;

public class AllConsultationsView {
    private List<ConsultationDto> consultationDtoList;


    public List<ConsultationDto> getConsultationDtoList() {
        return consultationDtoList;
    }

    public void setConsultationDtoList(List<ConsultationDto> consultationDtoList) {
        this.consultationDtoList = consultationDtoList;
    }
}
