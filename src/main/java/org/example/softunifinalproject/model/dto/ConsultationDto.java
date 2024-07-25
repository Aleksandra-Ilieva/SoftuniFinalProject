package org.example.softunifinalproject.model.dto;

import jakarta.validation.constraints.FutureOrPresent;
import org.example.softunifinalproject.validation.annotation.DateInWorkingDays;
import org.example.softunifinalproject.validation.annotation.TimeInWorkingHours;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class ConsultationDto {


    private long id;

    private String email;

    private String username;

    @FutureOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @DateInWorkingDays
    private LocalDate date;
    @DateTimeFormat(pattern = "hh:mm a")
    @TimeInWorkingHours()
    private LocalTime time;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ConsultationDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
