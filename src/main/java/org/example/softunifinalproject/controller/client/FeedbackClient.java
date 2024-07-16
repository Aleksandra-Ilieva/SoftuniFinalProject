package org.example.softunifinalproject.controller.client;

import org.example.softunifinalproject.model.dto.FeedbackDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
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
        String url = "http://localhost:8081/api/v1/feedback";

        try {
            ResponseEntity<List<FeedbackDto>> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<FeedbackDto>>() {}
            );

            return responseEntity.getBody();
        } catch (HttpClientErrorException e) {
            // Handle HTTP errors
            System.err.println("HTTP error occurred: " + e.getStatusCode() + " " + e.getStatusText());
        } catch (RestClientException e) {
            // Handle other errors
            System.err.println("Error occurred while making the request: " + e.getMessage());
        }

        return Collections.emptyList(); // Return an empty list in case of errors
    }
}
