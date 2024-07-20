package org.example.softunifinalproject.model.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Name;
import org.example.softunifinalproject.validation.annotation.UniqueEmail;
import org.example.softunifinalproject.validation.annotation.UniqueUsername;
import org.hibernate.validator.constraints.Length;

public class UserRegisterDto {

    @NotBlank(message = "Username must not be blank!")
    @Length(min = 2,max = 20,message = "FullName  must be between 2 and 20 characters.")
    private String fullName;


    @NotBlank(message = "Username must not be blank!")
    @Length(min = 2,max = 20,message = "Username  must be between 2 and 20 characters.")
    @UniqueUsername
    private String username;

    @Email(message = "Invalid email")
    @NotBlank(message = "Email can not be empty")
    @UniqueEmail
    private String email;

    @Length(min = 3, max = 20,message = "Password length must be between 3 and 20 characters!")
    @NotNull
    private String password;

    @NotBlank
    private String confirmPassword;


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
