package org.example.softunifinalproject.controler;

import jakarta.validation.Valid;
import org.example.softunifinalproject.model.dto.SetRoleDto;
import org.example.softunifinalproject.model.dto.ViewAllUsersDto;
import org.example.softunifinalproject.repository.RoleRepository;
import org.example.softunifinalproject.service.RoleService;
import org.example.softunifinalproject.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RoleController {
    private final RoleService roleService;
    public final UserService userService;

    public RoleController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }


    @GetMapping("/admin")
    public String admin(Model model) {
        ViewAllUsersDto viewAllUsersDto = this.userService.getAllUsers();
        model.addAttribute("viewAllUsersDto", viewAllUsersDto);

        return "admin";
    }
    @PostMapping("/admin/set/role")
    public String setRole(@Valid SetRoleDto setRoleDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("setRoleDto", setRoleDto)
                    .addFlashAttribute("org.springframework.validation.BindingResult.setRoleDto", bindingResult);
            return "redirect:/admin";
        }
       this.roleService.setRole(setRoleDto);
        return "redirect:/admin";
    }


    @ModelAttribute
    public SetRoleDto setRoleDto() {
        return new SetRoleDto();
    }
}
