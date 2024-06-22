package org.example.softunifinalproject.init;

import org.example.softunifinalproject.model.entity.Role;
import org.example.softunifinalproject.model.entity.User;
import org.example.softunifinalproject.model.enums.RoleType;
import org.example.softunifinalproject.repository.RoleRepository;
import org.example.softunifinalproject.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DbInitialInit implements CommandLineRunner {


    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public DbInitialInit(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public void run(String... args) throws Exception {
        if(roleRepository.count() == 0 && userRepository.count() == 0) {

            Role role = new Role();
            role.setRoleType(RoleType.USER);
            roleRepository.save(role);

            Role role2 = new Role();
            role2.setRoleType(RoleType.ADMIN);
            roleRepository.save(role2);

            Role role3 = new Role();
            role3.setRoleType(RoleType.DOCTOR);
            roleRepository.save(role3);

            List<Role> roles = roleRepository.findAll();
            User user = new User();
            user.setEmail("admin@adminov.com");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setFullName("Admin Adminov");
            user.setUsername("admin");
            user.setRoles(roles);
            userRepository.save(user);

        }

    }
}
