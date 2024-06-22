package org.example.softunifinalproject.config;

import org.example.softunifinalproject.repository.ConsultationRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CleanupScheduler {
    private final ConsultationRepository consultationRepository;

    public CleanupScheduler(ConsultationRepository consultationRepository) {
        this.consultationRepository = consultationRepository;
    }

    @Scheduled(fixedDelay = 7200000) // 7200000 ms = 2 hours
    public void cleanUpDatabase() {
        consultationRepository.deleteConsultedAppointments();
        System.out.println("Database cleanup completed at " + System.currentTimeMillis());
    }
}
