package org.example.softunifinalproject.controler;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/services")
    public String services() {
        return "services";
    }

    @GetMapping("/prices")
    public String prices() {
        return "prices";
    }




    @GetMapping("/about")
    public String about() {
        return "aboutUs";
    }

    @GetMapping("/contact")
    public String showContactForm(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails != null) {
            String userEmail = userDetails.getUsername(); // Получаване на имейл адреса на потребителя
            model.addAttribute("userEmail", userEmail); // Предаване на имейл адреса на шаблона
        }
        return "contactUs";
    }
}
