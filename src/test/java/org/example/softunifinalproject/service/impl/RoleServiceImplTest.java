package org.example.softunifinalproject.service.impl;

import org.example.softunifinalproject.model.dto.SetRoleDto;
import org.example.softunifinalproject.model.entity.Role;
import org.example.softunifinalproject.model.entity.User;
import org.example.softunifinalproject.model.enums.RoleType;
import org.example.softunifinalproject.repository.RoleRepository;
import org.example.softunifinalproject.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetRoleForNormalUser() {
        // Arrange
        Role expectedRole = new Role();
        expectedRole.setRoleType(RoleType.USER);
        when(roleRepository.findByRoleType(RoleType.USER)).thenReturn(expectedRole);

        // Act
        Role result = roleService.getRoleForNormalUser();

        // Assert
        assertEquals(expectedRole, result, "The role should match the expected role");
        verify(roleRepository).findByRoleType(RoleType.USER);
    }

    @Test
    void testSetRoleWhenRoleNotAssigned() {
        // Arrange
        SetRoleDto dto = new SetRoleDto();
        dto.setEmail("test@example.com");
        dto.setUsername("testuser");
        dto.setRoleType("admin");
        User user = new User();
        Role role = new Role();
        role.setRoleType(RoleType.ADMIN);

        when(userRepository.findUserByEmailAndUsername(dto.getEmail(), dto.getUsername())).thenReturn(user);
        when(roleRepository.findByRoleType(RoleType.ADMIN)).thenReturn(role);

        // Act
        boolean result = roleService.setRole(dto);

        // Assert
        assertTrue(result, "The role should be successfully assigned");
        assertTrue(user.getRoles().contains(role), "The user should have the assigned role");
        verify(userRepository).findUserByEmailAndUsername(dto.getEmail(), dto.getUsername());
        verify(roleRepository).findByRoleType(RoleType.ADMIN);
        verify(userRepository).save(user);
    }

    @Test
    void testSetRoleWhenRoleAlreadyAssigned() {
        // Arrange
        SetRoleDto dto = new SetRoleDto();
        dto.setEmail("test@example.com");
        dto.setUsername("testuser");
        dto.setRoleType("admin");
        User user = new User();
        Role role = new Role();
        role.setRoleType(RoleType.ADMIN);
        user.getRoles().add(role);

        when(userRepository.findUserByEmailAndUsername(dto.getEmail(), dto.getUsername())).thenReturn(user);
        when(roleRepository.findByRoleType(RoleType.ADMIN)).thenReturn(role);

        // Act
        boolean result = roleService.setRole(dto);

        // Assert
        assertFalse(result, "The role should not be reassigned if it already exists");
        verify(userRepository).findUserByEmailAndUsername(dto.getEmail(), dto.getUsername());
        verify(roleRepository).findByRoleType(RoleType.ADMIN);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testValidateUsernameAndEmailWhenUserExists() {
        // Arrange
        SetRoleDto dto = new SetRoleDto();
        dto.setEmail("test@example.com");
        dto.setUsername("testuser");
        dto.setRoleType("admin");

        User user = new User();

        when(userRepository.findUserByEmailAndUsername(dto.getEmail(), dto.getUsername())).thenReturn(user);

        // Act
        boolean result = roleService.validateUsernameAndEmail(dto);

        // Assert
        assertTrue(result, "The user should be validated as existing");
        verify(userRepository).findUserByEmailAndUsername(dto.getEmail(), dto.getUsername());
    }

    @Test
    void testValidateUsernameAndEmailWhenUserDoesNotExist() {
        // Arrange
        SetRoleDto dto = new SetRoleDto();
        dto.setEmail("test@example.com");
        dto.setUsername("testuser");
        dto.setRoleType("admin");

        when(userRepository.findUserByEmailAndUsername(dto.getEmail(), dto.getUsername())).thenReturn(null);

        // Act
        boolean result = roleService.validateUsernameAndEmail(dto);

        // Assert
        assertFalse(result, "The user should not be validated as existing");
        verify(userRepository).findUserByEmailAndUsername(dto.getEmail(), dto.getUsername());
    }

}