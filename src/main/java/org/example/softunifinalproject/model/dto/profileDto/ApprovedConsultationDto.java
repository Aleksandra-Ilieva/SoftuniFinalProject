package org.example.softunifinalproject.model.dto.profileDto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ApprovedConsultationDto {
    private long id;
    private LocalDate date;
    private LocalTime time;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
