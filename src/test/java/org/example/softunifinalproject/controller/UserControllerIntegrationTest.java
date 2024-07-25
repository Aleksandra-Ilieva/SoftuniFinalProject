package org.example.softunifinalproject.controller;

import org.example.softunifinalproject.model.dto.UserRegisterDto;
import org.example.softunifinalproject.model.entity.User;
import org.example.softunifinalproject.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;


    @Test
    public void testGetRegisterPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("registerDto"));
    }

    @Test
    public void testRegisterWithValidData() throws Exception {
        UserRegisterDto validDto = new UserRegisterDto();
        validDto.setUsername("validUser");
        validDto.setPassword("validPassword");
        validDto.setConfirmPassword("validPassword");
        validDto.setFullName("validPassword");
        validDto.setEmail("validPassword@abv.bg");

        // Добавяне на потребителя в базата данни
        // Предполага се, че имаш UserRepository, който ще използваме за съхраняване на потребители
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", validDto.getUsername())
                        .param("password", validDto.getPassword())
                        .param("confirmPassword", validDto.getPassword())
                        .param("email", "validPassword@abv.bg")
                        .param("fullName", "validUser").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        User user = userRepository.findByUsername(validDto.getUsername());
        assertNotNull(user);

    }

    @Test
    public void testRegisterUser_Failure() throws Exception {
        UserRegisterDto registerDto = new UserRegisterDto();
        // Настройте недействителни или липсващи полета тук, за да симулирате грешки

        mockMvc.perform(post("/register")
                        .flashAttr("registerDto", registerDto).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/register"))
                .andExpect(flash().attributeExists("registerDto"))
                .andExpect(flash().attributeExists("org.springframework.validation.BindingResult.registerDto"));
    }



    @Test
    public void testGetLoginPageWithoutError() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeDoesNotExist("error"));
    }

    @Test
    public void testGetLoginPageWithError() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login")
                        .param("error", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attribute("error", "Invalid username and password."));
    }
}
