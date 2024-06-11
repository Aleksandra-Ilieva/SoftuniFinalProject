package org.example.softunifinalproject.service.impl;

import org.example.softunifinalproject.model.entity.Role;
import org.example.softunifinalproject.model.enums.RoleType;
import org.example.softunifinalproject.repository.RoleRepository;
import org.example.softunifinalproject.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getRoleForNormalUser() {
        return roleRepository.findByRoleType(RoleType.USER);
    }
}
