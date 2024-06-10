package org.example.softunifinalproject.service.impl;

import org.example.softunifinalproject.model.User;
import org.example.softunifinalproject.model.enums.RoleType;
import org.example.softunifinalproject.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class ApplicationUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public ApplicationUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findUserByEmail(username).map(this::map).orElseThrow(() -> new
                UsernameNotFoundException("User with name " + username + " not found!"));
    }

    private UserDetails map(User user) {
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities(user));
    }

    private List<GrantedAuthority> authorities(User user) {

        return user.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleType().name())).collect(Collectors.toList());
    }


}
