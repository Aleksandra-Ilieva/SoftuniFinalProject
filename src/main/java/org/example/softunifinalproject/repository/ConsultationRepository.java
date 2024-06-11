package org.example.softunifinalproject.repository;

import org.example.softunifinalproject.model.entity.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
}
