package org.example.softunifinalproject.controller;

import org.example.softunifinalproject.model.dto.ConsultationDto;
import org.example.softunifinalproject.model.entity.Consultation;
import org.example.softunifinalproject.model.entity.Role;
import org.example.softunifinalproject.model.entity.User;
import org.example.softunifinalproject.model.enums.RoleType;
import org.example.softunifinalproject.repository.ConsultationRepository;
import org.example.softunifinalproject.repository.RoleRepository;
import org.example.softunifinalproject.repository.UserRepository;
import org.example.softunifinalproject.service.ConsultationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class ConsultationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ConsultationService consultationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ConsultationRepository consultationRepository;

    @BeforeEach
    public void setup() {
        consultationRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();

        // Setting up test data
        Role userRole = new Role();
        userRole.setRoleType(RoleType.USER);
        roleRepository.save(userRole);

        User testUser = new User();
        testUser.setUsername("testUser");
        testUser.setEmail("test@example.com");
        testUser.setFullName("test");
        testUser.setPassword("test");
        testUser.getRoles().add(userRole);
        userRepository.save(testUser);

        Consultation consultation = new Consultation();
        consultation.setAccepted(true);
        consultation.setDateTime(LocalDateTime.of(2024,7,30,10,0,0));
        this.consultationRepository.save(consultation);
    }

    @Test
    @WithMockUser(username = "testUser")
    public void testGetAppointmentPage() throws Exception {


        mockMvc.perform(get("/appointment"))
                .andExpect(status().isOk())
                .andExpect(view().name("appointment"))
                .andExpect(model().attributeExists("busySlots"));
    }

    @Test
    @WithMockUser(username = "testUser")
    public void testSaveAppointmentWithInvalidData() throws Exception {
        ConsultationDto invalidDto = new ConsultationDto();
        invalidDto.setDate(LocalDate.of(2024, 7, 28));
        invalidDto.setTime(LocalTime.of(8, 30));


        mockMvc.perform(post("/appointment")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("date", String.valueOf(invalidDto.getDate()))
                        .param("time", String.valueOf(invalidDto.getTime()))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/appointment"))
                .andExpect(flash().attributeExists("consultationDto"))
                .andExpect(flash().attributeExists("org.springframework.validation.BindingResult.consultationDto"));
    }
    }