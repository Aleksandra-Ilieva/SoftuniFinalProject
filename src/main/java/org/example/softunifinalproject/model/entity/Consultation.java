package org.example.softunifinalproject.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "consultations")
public class Consultation extends BaseEntity {

    @ManyToOne
    private User user;
    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column
    private Boolean isAccepted;

    @Column(name = "is_consulted")
    private Boolean isConsulted;

    public Boolean getConsulted() {
        return isConsulted;
    }

    public void setConsulted(Boolean consulted) {
        isConsulted = consulted;
    }

    public Boolean getAccepted() {
        return isAccepted;
    }

    public void setAccepted(Boolean accepted) {
        isAccepted = accepted;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
