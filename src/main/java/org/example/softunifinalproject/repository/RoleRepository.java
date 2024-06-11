package org.example.softunifinalproject.repository;

import org.example.softunifinalproject.model.entity.Role;
import org.example.softunifinalproject.model.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleType(RoleType roleType);
}
