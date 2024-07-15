package org.example.softunifinalproject.controller.client;

import org.example.softunifinalproject.model.dto.FeedbackDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class FeedbackClient {


    private final RestTemplate restTemplate;

    public FeedbackClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public FeedbackDto saveFeedback(FeedbackDto feedbackDto) {
       FeedbackDto feedbackDto1 = restTemplate.postForObject("http://localhost:8081/api/v1/feedback", feedbackDto, FeedbackDto.class);
        return feedbackDto1;
    }

    public void deleteFeedback(Long id) {
       restTemplate.delete("http://localhost:8081/api/v1/feedback/"+id);
    }

    public List<FeedbackDto> getLastTen() {
        List<FeedbackDto> list =(List<FeedbackDto>) restTemplate.getForObject("http://localhost:8081/api/v1/feedback/", List.class);

        return list;
    }
}
