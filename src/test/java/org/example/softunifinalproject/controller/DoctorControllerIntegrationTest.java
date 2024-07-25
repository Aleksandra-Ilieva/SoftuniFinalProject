package org.example.softunifinalproject.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class DoctorControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;


    @BeforeEach
    public void setup() {

    }


    @Test
    @WithMockUser(username = "doctorUser", roles = {"DOCTOR"})
    public void testDoctorPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/doctor-page").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("doctor-page"))
                .andExpect(model().attributeExists("consultations"))
                .andExpect(model().attributeExists("approvedConsultations"));
    }


}

