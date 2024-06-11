package org.example.softunifinalproject.config;

import org.example.softunifinalproject.model.enums.RoleType;
import org.example.softunifinalproject.repository.UserRepository;
import org.example.softunifinalproject.service.impl.ApplicationUserDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class AppConfig {
    private final UserRepository userRepository;

    public AppConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()// defines which pages will be authorized
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .requestMatchers("/", "/login", "/register", "/services","/about","/prices").permitAll()
                .requestMatchers("/admin").hasRole(RoleType.ADMIN.name())
                .requestMatchers("/doctor-page").hasAnyRole(RoleType.DOCTOR.name(),RoleType.ADMIN.name())
                .anyRequest().authenticated()
                .and().formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
                                .usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                                .defaultSuccessUrl("/").failureUrl("/login?error=true").permitAll()



                ).logout((logout)-> logout.logoutUrl("/logout").logoutSuccessUrl("/").invalidateHttpSession(true).permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new ApplicationUserDetailsService(userRepository);
    }


    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
