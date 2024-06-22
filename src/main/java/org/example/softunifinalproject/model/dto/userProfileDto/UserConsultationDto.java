package org.example.softunifinalproject.model.dto.userProfileDto;

import org.example.softunifinalproject.model.dto.ConsultationDto;

import java.util.List;

public class UserConsultationDto {
    private String username;
    private List<ConsultationDto> userConsultations;

    public List<ConsultationDto> getUserConsultations() {
        return userConsultations;
    }

    public void setUserConsultations(List<ConsultationDto> userConsultations) {
        this.userConsultations = userConsultations;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
