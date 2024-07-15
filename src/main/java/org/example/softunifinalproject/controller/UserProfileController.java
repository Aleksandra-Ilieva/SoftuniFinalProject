package org.example.softunifinalproject.controller;

import org.example.softunifinalproject.model.dto.userProfileDto.ConsultationMessageDto;
import org.example.softunifinalproject.model.dto.userProfileDto.UserConsultationDto;
import org.example.softunifinalproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class UserProfileController {
    private final UserService userService;

    public UserProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String profile(Principal principal, Model model) {
        UserConsultationDto userConsultationDto = this.userService.getUserProfile(principal);
        model.addAttribute("userConsultationDto", userConsultationDto);
        return "profile";
    }

    @GetMapping("/profile/cancel/{id}")
    public String cancel(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        ConsultationMessageDto message= this.userService.cancelUserConsultation(id);
        model.addAttribute("message", message);
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/profile";

    }

}
