package org.example.softunifinalproject.controller;

import org.example.softunifinalproject.model.dto.doctorPageDto.AllNewConsultationsDto;
import org.example.softunifinalproject.model.dto.doctorPageDto.ApprovedConsultationsDto;
import org.example.softunifinalproject.service.ConsultationService;
import org.example.softunifinalproject.service.EmailSenderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DoctorController {

    private final ConsultationService consultationService;


    public DoctorController(ConsultationService consultationService, EmailSenderService emailSenderService) {
        this.consultationService = consultationService;

    }

    @GetMapping("/doctor-page")
    public String doctorPage(Model model) {
        AllNewConsultationsDto consultationsView =this.consultationService.getAllConsultations();
        ApprovedConsultationsDto approvedConsultationsDto = this.consultationService.getAllApprovedConsultations();
        model.addAttribute("consultations", consultationsView);
        model.addAttribute("approvedConsultations", approvedConsultationsDto);
        return "doctor-page";
    }

    @GetMapping("/doctor-page/all/cancel/{id}")
    public String cancelConsultation(@PathVariable long id) {
        this.consultationService.cancelConsultation(id);
        return "redirect:/doctor-page";
    }

    @GetMapping("/doctor-page/all/approve/{id}")
    public String approveConsultation(@PathVariable long id) {
        this.consultationService.approve(id);

        return "redirect:/doctor-page";
    }


    @GetMapping("/doctor-page/all/delete/{id}")
    public String deleteConsultedAppointment(@PathVariable long id) {
        this.consultationService.setAsConsulted(id);
        return "redirect:/doctor-page";
    }
}
