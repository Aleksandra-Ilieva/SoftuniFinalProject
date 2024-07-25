package org.example.softunifinalproject.controller;

import org.example.softunifinalproject.model.dto.ConsultationDto;
import org.example.softunifinalproject.model.dto.profileDto.BusySlotsDto;
import org.example.softunifinalproject.model.entity.Role;
import org.example.softunifinalproject.model.entity.User;
import org.example.softunifinalproject.model.enums.RoleType;
import org.example.softunifinalproject.repository.ConsultationRepository;
import org.example.softunifinalproject.repository.RoleRepository;
import org.example.softunifinalproject.repository.UserRepository;
import org.example.softunifinalproject.service.ConsultationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

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

    @MockBean
    private ConsultationService consultationService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;
    @MockBean
    private ConsultationRepository consultationRepository;

    @BeforeEach
    public void setup() {
        consultationRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "testUser")
    public void testGetAppointmentPage() throws Exception {
        BusySlotsDto busySlotsDto = new BusySlotsDto();
        Mockito.when(consultationService.findBusySlots()).thenReturn(busySlotsDto);

        mockMvc.perform(get("/appointment").with(csrf()))
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

        Mockito.when(consultationService.saveAppointment(Mockito.any(ConsultationDto.class), Mockito.any(Principal.class)))
                .thenReturn(false);

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