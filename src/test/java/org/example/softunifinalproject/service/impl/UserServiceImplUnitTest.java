package org.example.softunifinalproject.service.impl;

import org.example.softunifinalproject.model.dto.UserDto;
import org.example.softunifinalproject.model.dto.UserRegisterDto;
import org.example.softunifinalproject.model.dto.adminPageDto.ViewAllUsersDto;
import org.example.softunifinalproject.model.dto.userProfileDto.ConsultationMessageDto;
import org.example.softunifinalproject.model.dto.userProfileDto.UserConsultationDto;
import org.example.softunifinalproject.model.entity.Consultation;
import org.example.softunifinalproject.model.entity.Role;
import org.example.softunifinalproject.model.entity.User;
import org.example.softunifinalproject.repository.ConsultationRepository;
import org.example.softunifinalproject.repository.UserRepository;
import org.example.softunifinalproject.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplUnitTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleService roleService;

    @Mock
    private ConsultationRepository consultationRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister_success() {
        UserRegisterDto registerDto = new UserRegisterDto();
        registerDto.setPassword("password");
        registerDto.setConfirmPassword("password");
        User user = new User();
        Role role = new Role();

        when(modelMapper.map(registerDto, User.class)).thenReturn(user);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(roleService.getRoleForNormalUser()).thenReturn(role);
        when(userRepository.save(any(User.class))).thenReturn(user);

        boolean result = userService.register(registerDto);

        assertTrue(result);
        assertEquals("encodedPassword", passwordEncoder.encode("password"));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testRegister_passwordsDoNotMatch() {
        UserRegisterDto registerDto = new UserRegisterDto();
        registerDto.setPassword("password");
        registerDto.setConfirmPassword("differentPassword");

        boolean result = userService.register(registerDto);

        assertFalse(result);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testGetAllUsers() {
        List<User> users = new ArrayList<>();
        User user1 = new User();
        User user2 = new User();
        users.add(user1);
        users.add(user2);

        when(userRepository.findAll()).thenReturn(users);

        ViewAllUsersDto result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.getUsers().size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserProfile() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("user@example.com");

        User user = new User();
        user.setUsername("username");

        Set<Consultation> consultations = new HashSet<>();
        Consultation consultation = new Consultation();
        consultation.setId(1L);
        consultation.setDateTime(LocalDateTime.now().plusDays(1));
        consultation.setAccepted(true);
        consultation.setConsulted(null);
        consultations.add(consultation);
        user.setConsultations(consultations);
        user.setConsultations(consultations);

        when(userRepository.findUserByEmail("user@example.com")).thenReturn(Optional.of(user));

        UserConsultationDto result = userService.getUserProfile(principal);

        assertNotNull(result);
        assertEquals("username", result.getUsername());
        assertEquals(1, result.getUserConsultations().size());
        verify(userRepository, times(1)).findUserByEmail("user@example.com");
    }

    @Test
    void testCancelUserConsultation_success() {
        Consultation consultation = new Consultation();
        consultation.setId(1L);
        consultation.setDateTime(LocalDateTime.now().plusDays(1));

        when(consultationRepository.findById(1L)).thenReturn(Optional.of(consultation));

        ConsultationMessageDto result = userService.cancelUserConsultation(1L);

        assertTrue(result.getCanceled());
        verify(consultationRepository, times(1)).delete(consultation);
    }

    @Test
    void testCancelUserConsultation_tooLate() {
        Consultation consultation = new Consultation();
        consultation.setId(1L);
        consultation.setDateTime(LocalDateTime.now().plusHours(1));

        when(consultationRepository.findById(1L)).thenReturn(Optional.of(consultation));

        ConsultationMessageDto result = userService.cancelUserConsultation(1L);

        assertFalse(result.getCanceled());
        verify(consultationRepository, never()).delete(consultation);
    }

    @Test
    void testGetUser_found() {
        User user = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserDto.class)).thenReturn(new UserDto());

        UserDto result = userService.getUser(1L);

        assertNotNull(result);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUser_notFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        UserDto result = userService.getUser(1L);

        assertNull(result);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testValidateEmail_available() {
        when(userRepository.findByEmail("user@example.com")).thenReturn(null);

        boolean result = userService.validateEmail("user@example.com");

        assertTrue(result);
        verify(userRepository, times(1)).findByEmail("user@example.com");
    }

    @Test
    void testValidateEmail_taken() {
        when(userRepository.findByEmail("user@example.com")).thenReturn(new User());

        boolean result = userService.validateEmail("user@example.com");

        assertFalse(result);
        verify(userRepository, times(1)).findByEmail("user@example.com");
    }

    @Test
    void testValidateUsername_available() {
        when(userRepository.findByUsername("username")).thenReturn(null);

        boolean result = userService.validateUsername("username");

        assertTrue(result);
        verify(userRepository, times(1)).findByUsername("username");
    }

    @Test
    void testValidateUsername_taken() {
        when(userRepository.findByUsername("username")).thenReturn(new User());

        boolean result = userService.validateUsername("username");

        assertFalse(result);
        verify(userRepository, times(1)).findByUsername("username");
    }
}





