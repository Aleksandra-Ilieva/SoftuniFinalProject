package org.example.softunifinalproject.repository;

import org.example.softunifinalproject.model.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
