package org.example.softunifinalproject.controller;

import jakarta.validation.Valid;
import org.example.softunifinalproject.model.dto.ConsultationDto;
import org.example.softunifinalproject.model.dto.profileDto.BusySlotsDto;
import org.example.softunifinalproject.service.ConsultationService;
import org.example.softunifinalproject.service.EmailSenderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class ConsultationController {
    private final ConsultationService consultationService;

    public ConsultationController(ConsultationService consultationService, EmailSenderService emailSenderService) {
        this.consultationService = consultationService;
    }

    @GetMapping("/appointment")
    public String appointment(Model model) {
        BusySlotsDto busySlots= this.consultationService.findBusySlots();
        model.addAttribute("busySlots", busySlots);
        return "appointment";
    }

    @PostMapping("/appointment")
    public String saveAppointment(@Valid ConsultationDto consultationDto, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model, Principal principal) {
        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("consultationDto", consultationDto)
                    .addFlashAttribute("org.springframework.validation.BindingResult.consultationDto", bindingResult);

            return "redirect:/appointment";
        }
        boolean isAlreadyBooked=this.consultationService.checkIfAlreadyBooked(consultationDto);


            redirectAttributes.addFlashAttribute("isAlreadyBooked", isAlreadyBooked);
            if(!isAlreadyBooked){
                boolean isSend = this.consultationService.saveAppointment(consultationDto, principal);
                redirectAttributes.addFlashAttribute("isSend", isSend)
                        .addFlashAttribute("consultationDto", consultationDto);
            }



        return "redirect:/appointment";
    }


    @ModelAttribute
    public ConsultationDto consultationDto(){
        return new ConsultationDto();
    }



}
