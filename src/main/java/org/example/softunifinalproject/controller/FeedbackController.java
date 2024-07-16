package org.example.softunifinalproject.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class FeedbackController {
    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService contactUsService) {
        this.feedbackService = contactUsService;
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

        this.feedbackService.saveFeedback(feedbackDto);
        redirectAttributes.addFlashAttribute("isSaved", true);

        return "redirect:/contact";
    }

    @GetMapping("/admin/feedback")
    public String showFeedbacks( Model model) {
        List<FeedbackDto> feedbackDtoList = this.feedbackService.getLastTen();
        model.addAttribute("feedbackDtoList", feedbackDtoList);
        return "adminFeedbackPanel";
    }

    @GetMapping("/admin/feedback/{id}")
    public String cancelConsultation(@PathVariable long id) {
        this.feedbackService.deleteFeedback(id);
        return "redirect:/admin/feedback";
    }

    @ModelAttribute
    public FeedbackDto feedbackDto(Model model) {
        return new FeedbackDto();
    }


}
