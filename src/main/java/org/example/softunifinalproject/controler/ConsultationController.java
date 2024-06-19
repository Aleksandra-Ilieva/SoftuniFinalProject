package org.example.softunifinalproject.controler;

import jakarta.validation.Valid;
import org.example.softunifinalproject.model.dto.AllConsultationsView;
import org.example.softunifinalproject.model.dto.ConsultationDto;
import org.example.softunifinalproject.model.entity.Consultation;
import org.example.softunifinalproject.service.ConsultationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ConsultationController {
    private final ConsultationService consultationService;

    public ConsultationController(ConsultationService consultationService) {
        this.consultationService = consultationService;
    }

    @GetMapping("/doctor-page")
    public String doctorPage(Model model) {
        AllConsultationsView consultationsView =this.consultationService.getAllConsultations();
        model.addAttribute("consultations", consultationsView);
        return "doctor-page";
    }

    @GetMapping("/doctor-page/all/{id}")
    public String cancelConsultation(@PathVariable long id, Model model) {
        this.consultationService.cancelConsultation(id);
        return "redirect:/doctor-page";
    }



    @PostMapping("/doctor-page/all")
    public String postAllAppointments(){

        return "";
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
