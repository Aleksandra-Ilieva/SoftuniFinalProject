package org.example.softunifinalproject.service.impl;

import org.example.softunifinalproject.model.dto.FeedbackDto;
import org.example.softunifinalproject.model.entity.Feedback;
import org.example.softunifinalproject.repository.FeedbackRepository;
import org.example.softunifinalproject.service.FeedbackService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    private final ModelMapper modelMapper;
    private final FeedbackRepository feedbackRepository;

    public FeedbackServiceImpl(ModelMapper modelMapper, FeedbackRepository feedbackRepository) {
        this.modelMapper = modelMapper;
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public void saveFeedback(FeedbackDto feedbackDto) {
      Feedback feedback=  this.modelMapper.map(feedbackDto, Feedback.class);
      this.feedbackRepository.save(feedback);

    }
}
