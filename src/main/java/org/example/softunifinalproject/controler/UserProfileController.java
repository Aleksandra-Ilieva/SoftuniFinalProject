package org.example.softunifinalproject.controler;

import org.example.softunifinalproject.model.dto.profileDto.UserConsultationDto;
import org.example.softunifinalproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class UserProfileController {
    private final UserService userService;

    public UserProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String profile(Principal principal, Model model) {
        UserConsultationDto userConsultationDto= this.userService.getUserProfile(principal);
        model.addAttribute("userConsultationDto", userConsultationDto);
        return "profile";
    }
}
