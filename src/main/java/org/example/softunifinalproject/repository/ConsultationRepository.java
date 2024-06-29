package org.example.softunifinalproject.repository;

import jakarta.transaction.Transactional;
import org.example.softunifinalproject.model.entity.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Consultation e WHERE e.isConsulted = true")
    void deleteConsultedAppointments();

    boolean existsByDateTime(LocalDateTime slot);
}
