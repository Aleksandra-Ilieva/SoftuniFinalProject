package org.example.softunifinalproject.service.impl;

import org.example.softunifinalproject.controller.client.FeedbackClient;
import org.example.softunifinalproject.model.dto.FeedbackDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FeedbackServiceImplUnitTest {

    @Mock
    private FeedbackClient feedbackClient;

    @InjectMocks
    private FeedbackServiceImpl feedbackService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveFeedback() {
        FeedbackDto feedbackDto = new FeedbackDto();
        when(feedbackClient.saveFeedback(feedbackDto)).thenReturn(feedbackDto);

        FeedbackDto result = feedbackService.saveFeedback(feedbackDto);

        assertEquals(feedbackDto, result);
        verify(feedbackClient, times(1)).saveFeedback(feedbackDto);
    }

    @Test
    void testDeleteFeedback() {
        Long id = 1L;

        feedbackService.deleteFeedback(id);

        verify(feedbackClient, times(1)).deleteFeedback(id);
    }

    @Test
    void testGetLastTen() {
        List<FeedbackDto> feedbackList = List.of(new FeedbackDto(), new FeedbackDto(), new FeedbackDto());
        when(feedbackClient.getLastTen()).thenReturn(feedbackList);

        List<FeedbackDto> result = feedbackService.getLastTen();

        assertEquals(feedbackList, result);
        verify(feedbackClient, times(1)).getLastTen();
    }

}