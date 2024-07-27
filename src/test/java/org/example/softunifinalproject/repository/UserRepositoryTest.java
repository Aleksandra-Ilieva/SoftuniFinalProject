package org.example.softunifinalproject.repository;

import org.example.softunifinalproject.model.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();

        User user = new User();
        user.setEmail("test78@example.com");
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setFullName("Ivan");
        userRepository.save(user);

    }



    @Test
    public void testFindUserByUsername() {
        User user = userRepository.findByUsername("testuser");
        assertEquals("test78@example.com", user.getEmail(), "Email should be 'test@example.com'");
    }

    @Test
    public void testFindUserByEmailAndUsername() {
        User user = userRepository.findUserByEmailAndUsername("test78@example.com", "testuser");
        assertEquals("password123", user.getPassword(), "Password should be 'password123'");
    }



}