package org.example.softunifinalproject.controler;

import jakarta.validation.Valid;
import org.example.softunifinalproject.model.dto.AllNewConsultationsDto;
import org.example.softunifinalproject.model.dto.ApprovedConsultationsDto;
import org.example.softunifinalproject.model.dto.ConsultationDto;
import org.example.softunifinalproject.service.ConsultationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ConsultationController {
    private final ConsultationService consultationService;

    public ConsultationController(ConsultationService consultationService) {
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

    @GetMapping("/appointment")
    public String appointment() {
        return "appointment";
    }

    @PostMapping("/appointment")
    public String getAppointment(@Valid ConsultationDto consultationDto, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("consultationDto", consultationDto)
                    .addFlashAttribute("org.springframework.validation.BindingResult.consultationDto", bindingResult);

            return "redirect:/appointment";
        }
      boolean isSend=  this.consultationService.saveAppointment(consultationDto);
        model.addAttribute("isSend", isSend);
        if(isSend){
            redirectAttributes.addFlashAttribute("isSend", true)
            .addFlashAttribute("consultationDto", consultationDto);
        }else {
            redirectAttributes.addFlashAttribute("isSend", false)
                    .addFlashAttribute("consultationDto", consultationDto);
        }
        return "redirect:/appointment";
    }


    @ModelAttribute
    public ConsultationDto consultationDto(){
        return new ConsultationDto();
    }
}
