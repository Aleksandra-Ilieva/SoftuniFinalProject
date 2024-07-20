package org.example.softunifinalproject.controller;

import jakarta.validation.Valid;
import org.example.softunifinalproject.model.dto.UserRegisterDto;
import org.example.softunifinalproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        if (!model.containsAttribute("registerDto")) {
            model.addAttribute("registerDto", new UserRegisterDto());
        }
        return "register";

    }

    @GetMapping("/login")
    public String login(@RequestParam(name = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username and password.");
        }
        return "login";
    }



    @PostMapping("/register")
    public String register(@Valid UserRegisterDto registerDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            redirectAttributes
                    .addFlashAttribute("registerDto", registerDto)
                    .addFlashAttribute("org.springframework.validation.BindingResult.registerDto", bindingResult);
            return "redirect:/register";
        }

        boolean isSuccessful =this.userService.register(registerDto);
        if(isSuccessful){
            return "redirect:/login";
        }
        redirectAttributes
                .addFlashAttribute("isSuccessful", false)
                .addFlashAttribute("org.springframework.validation.BindingResult.registerDto", bindingResult);
        return "redirect:/register";



    }


    @ModelAttribute
    public UserRegisterDto registerDto(){
        return new UserRegisterDto();
    }
}
