package org.example.softunifinalproject.controller;

import jakarta.validation.Valid;
import org.example.softunifinalproject.model.dto.SetRoleDto;
import org.example.softunifinalproject.model.dto.UserDto;
import org.example.softunifinalproject.model.dto.adminPageDto.ViewAllUsersDto;
import org.example.softunifinalproject.service.RoleService;
import org.example.softunifinalproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class RoleController {
    private final RoleService roleService;
    public final UserService userService;
    private UserDto userDto;

    public RoleController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }


    @GetMapping()
    public String admin(Model model) {
        ViewAllUsersDto viewAllUsersDto = this.userService.getAllUsers();
        model.addAttribute("viewAllUsersDto", viewAllUsersDto);

        return "admin";
    }

    @PostMapping("/set/role")
    public String setRole(@Valid SetRoleDto setRoleDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("setRoleDto", setRoleDto)
                    .addFlashAttribute("org.springframework.validation.BindingResult.setRoleDto", bindingResult);
            return "redirect:/admin";
        }
        boolean isValid = this.roleService.validateUsernameAndEmail(setRoleDto);
        redirectAttributes.addFlashAttribute("isValid", isValid);
        if (!isValid) {
            return "redirect:/admin";
        }

        if(isValid){
            boolean isSet = this.roleService.setRole(setRoleDto);
            redirectAttributes.addFlashAttribute("isSet", isSet);

        }


        return "redirect:/admin";

    }


    @ModelAttribute
    public SetRoleDto setRoleDto() {
        return new SetRoleDto();
    }



}
