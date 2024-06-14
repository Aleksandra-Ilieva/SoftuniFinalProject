package org.example.softunifinalproject.service;

import org.example.softunifinalproject.model.dto.SetRoleDto;
import org.example.softunifinalproject.model.entity.Role;

public interface RoleService {
    Role getRoleForNormalUser();

    void setRole(SetRoleDto dto);
}
