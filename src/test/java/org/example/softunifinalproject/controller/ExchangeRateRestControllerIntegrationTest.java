package org.example.softunifinalproject.controller;

import org.example.softunifinalproject.model.entity.Rate;
import org.example.softunifinalproject.repository.RateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class ExchangeRateRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;



    @Autowired
    private RateRepository rateRepository;

    @BeforeEach
    public void setUp() {


        Rate rate1 = new Rate();
        rate1.setValue(2);
        rate1.setName("USD");
        rate1.setCreatedOn(LocalDateTime.now());
        Rate rate2= new Rate();
        rate2.setValue(2.1);
        rate2.setName("EUR");
        rate2.setCreatedOn(LocalDateTime.now());

        List<Rate> rates = Arrays.asList(rate1, rate2);

        rateRepository.saveAll(rates);
    }

    @Test
    public void testGetAllRatesReturnsEUR() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rates").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("EUR"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.value").value(2.1));
    }

    @Test
    public void testGetAllRatesReturnsNotFound() throws Exception {
        rateRepository.deleteAll();
        mockMvc.perform(MockMvcRequestBuilders.get("/rates").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}