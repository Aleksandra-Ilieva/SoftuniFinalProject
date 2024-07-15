package org.example.softunifinalproject.service.impl;

import org.example.softunifinalproject.controller.client.FeedbackClient;
import org.example.softunifinalproject.model.dto.FeedbackDto;
import org.example.softunifinalproject.service.FeedbackService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {
//    private final ModelMapper modelMapper;
//    private final FeedbackRepository feedbackRepository;
    private final FeedbackClient feedbackClient;

    public FeedbackServiceImpl(/*ModelMapper modelMapper, FeedbackRepository feedbackRepository, */FeedbackClient feedbackClient) {
//        this.modelMapper = modelMapper;
//        this.feedbackRepository = feedbackRepository;
        this.feedbackClient = feedbackClient;
    }

    @Override
    public FeedbackDto saveFeedback(FeedbackDto feedbackDto) {
//      Feedback feedback=  this.modelMapper.map(feedbackDto, Feedback.class);
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
