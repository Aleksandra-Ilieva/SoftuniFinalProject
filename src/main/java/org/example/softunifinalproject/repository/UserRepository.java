package org.example.softunifinalproject.repository;

import aj.org.objectweb.asm.commons.Remapper;
import org.example.softunifinalproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String username);
}
