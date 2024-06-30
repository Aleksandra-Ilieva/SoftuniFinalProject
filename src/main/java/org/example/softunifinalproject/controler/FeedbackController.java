package org.example.softunifinalproject.controler;

import jakarta.validation.Valid;
import org.example.softunifinalproject.model.dto.FeedbackDto;
import org.example.softunifinalproject.service.FeedbackService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class FeedbackController {
    private final FeedbackService contactUsService;

    public FeedbackController(FeedbackService contactUsService) {
        this.contactUsService = contactUsService;
    }


    @GetMapping("/contact")
    public String showContactForm(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails != null) {
            String userEmail = userDetails.getUsername(); // Получаване на имейл адреса на потребителя
            model.addAttribute("userEmail", userEmail); // Предаване на имейл адреса на шаблона
        }
        return "contactUs";
    }


    @PostMapping("/contact")
    public String contact(@Valid FeedbackDto feedbackDto, BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal) {
        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("feedbackDto", feedbackDto)
                    .addFlashAttribute("org.springframework.validation.BindingResult.feedbackDto", bindingResult);
            return "redirect:/contact";
        }

        this.contactUsService.saveFeedback(feedbackDto);
        redirectAttributes.addFlashAttribute("isSaved", true);

        return "redirect:/contact";
    }

    @ModelAttribute
    public FeedbackDto feedbackDto() {
        return new FeedbackDto();
    }
}
