package org.example.softunifinalproject.service.impl;

import org.example.softunifinalproject.controller.client.FeedbackClient;
import org.example.softunifinalproject.model.dto.FeedbackDto;
import org.example.softunifinalproject.service.FeedbackService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackClient feedbackClient;

    public FeedbackServiceImpl(FeedbackClient feedbackClient) {
        this.feedbackClient = feedbackClient;
    }

    @Override
    public FeedbackDto saveFeedback(FeedbackDto feedbackDto) {
     return this.feedbackClient.saveFeedback(feedbackDto);

    }

    @Override
    public void deleteFeedback(Long id) {
        this.feedbackClient.deleteFeedback(id);

    }

    @Override
    public List<FeedbackDto> getLastTen() {
        return this.feedbackClient.getLastTen();
    }
}
