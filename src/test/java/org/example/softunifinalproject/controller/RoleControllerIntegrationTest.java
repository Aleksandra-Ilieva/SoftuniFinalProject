package org.example.softunifinalproject.controller;

import org.example.softunifinalproject.model.dto.SetRoleDto;
import org.example.softunifinalproject.model.entity.Role;
import org.example.softunifinalproject.model.entity.User;
import org.example.softunifinalproject.model.enums.RoleType;
import org.example.softunifinalproject.repository.RoleRepository;
import org.example.softunifinalproject.repository.UserRepository;
import org.example.softunifinalproject.service.RoleService;
import org.example.softunifinalproject.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class RoleControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void setup() {
        roleRepository.deleteAll();
        userRepository.deleteAll();


        // Set up test roles and users
        Role userRole = new Role();
        userRole.setRoleType(RoleType.USER);
        roleRepository.save(userRole);;
        List<Role> roles = new ArrayList<>();
        roles.add(userRole);
        this.roleRepository.saveAll(roles);

        User testUser = new User();
        testUser.setUsername("testUser");
        testUser.setPassword("password");
        testUser.setFullName("test");
        testUser.setEmail("test@example.com");
        testUser.setRoles(roles);
        userRepository.save(testUser);
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"ADMIN"})
    public void testAdminPage() throws Exception {

        mockMvc.perform(get("/admin").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"))
                .andExpect(model().attributeExists("viewAllUsersDto"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"ADMIN"})
    public void testSetRoleWithInvalidData() throws Exception {
        SetRoleDto setRoleDto = new SetRoleDto();
        setRoleDto.setEmail("test@example.com");
        setRoleDto.setUsername("testUserDVDVV");
        setRoleDto.setRoleType("DOCTOR");

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/set/role").with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("email", setRoleDto.getEmail())
                        .param("username", setRoleDto.getUsername())
                        .param("roleType", setRoleDto.getRoleType())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"))
                .andExpect(flash().attributeExists("isValid"))
                .andExpect(flash().attribute("isValid", false));
    }

    @Test
    @WithMockUser(username = "testUser")
    public void testSetRoleWithValidData() throws Exception {
        SetRoleDto setRoleDto = new SetRoleDto();
        setRoleDto.setEmail("test@example.com");
        setRoleDto.setUsername("testUser");
        setRoleDto.setRoleType("USER"); // Valid role type

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/set/role").with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("email", setRoleDto.getEmail())
                        .param("username", setRoleDto.getUsername())
                        .param("roleType", setRoleDto.getRoleType())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"))
                .andExpect(flash().attributeExists("isValid"))
                .andExpect(flash().attribute("isValid", true));
    }

}