package org.example.softunifinalproject.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import javax.crypto.Mac;

public class FeedbackDto {

    @NotNull
    @NotBlank(message = "Full Name must not be blank!")
    private String fullName;

    @NotNull
    @NotBlank(message = "Email must not be blank!")
    private String email;

    @NotNull
    @NotBlank(message = "Message must not be blank!")
    private String message;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
