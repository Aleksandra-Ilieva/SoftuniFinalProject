package org.example.softunifinalproject.model.dto.doctorPageDto;

import org.example.softunifinalproject.model.dto.ConsultationDto;

import java.util.List;

public class AllNewConsultationsDto {
    private List<ConsultationDto> consultationDtoList;


    public List<ConsultationDto> getConsultationDtoList() {
        return consultationDtoList;
    }

    public void setConsultationDtoList(List<ConsultationDto> consultationDtoList) {
        this.consultationDtoList = consultationDtoList;
    }
}
