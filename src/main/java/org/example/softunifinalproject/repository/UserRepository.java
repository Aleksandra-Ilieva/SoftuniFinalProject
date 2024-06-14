package org.example.softunifinalproject.repository;

import org.example.softunifinalproject.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String username);

    User findUserByEmailAndUsername(String email, String username);
}
