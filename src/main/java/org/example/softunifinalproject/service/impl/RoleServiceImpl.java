package org.example.softunifinalproject.service.impl;

import org.example.softunifinalproject.model.dto.SetRoleDto;
import org.example.softunifinalproject.model.entity.Role;
import org.example.softunifinalproject.model.entity.User;
import org.example.softunifinalproject.model.enums.RoleType;
import org.example.softunifinalproject.repository.RoleRepository;
import org.example.softunifinalproject.repository.UserRepository;
import org.example.softunifinalproject.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public RoleServiceImpl(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Role getRoleForNormalUser() {
        return roleRepository.findByRoleType(RoleType.USER);
    }

    @Override
    public boolean setRole(SetRoleDto dto) {
      User user=  this.userRepository.findUserByEmailAndUsername(dto.getEmail(),dto.getUsername());
      RoleType roleType = RoleType.valueOf(dto.getRoleType().toUpperCase());
      Role role= this.roleRepository.findByRoleType(roleType);
      if(user.getRoles().contains(role)){
          return false;
      }
      user.getRoles().add(role);
      this.userRepository.save(user);
      return true;
    }


}
