package org.example.softunifinalproject.service;

import org.example.softunifinalproject.model.dto.FeedbackDto;

import java.util.List;

public interface FeedbackService {

    FeedbackDto saveFeedback(FeedbackDto contactUsDto);
    void deleteFeedback(Long id);
    List<FeedbackDto> getLastTen();
}
