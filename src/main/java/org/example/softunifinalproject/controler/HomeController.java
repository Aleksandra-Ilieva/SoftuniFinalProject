package org.example.softunifinalproject.controler;

import org.springframework.stereotype.Controller;
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


    @GetMapping("/appointment")
    public String appointment() {
        return "appointment";
    }
    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }
    @GetMapping("/doctor-page")
    public String doctorPage() {
        return "doctor-page";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/about")
    public String about() {
        return "aboutUs";
    }
}
