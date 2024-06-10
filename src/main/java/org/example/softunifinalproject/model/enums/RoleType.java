package org.example.softunifinalproject.model.enums;

public enum RoleType {
    ADMIN("admin"),USER("user"),DOCTOR("doctor");


    public String value;

    RoleType(String value) {
        this.value = value;
    }
}
