package org.example.softunifinalproject.model.dto;

public class RateDto {
    private String name;
    private Double value;

    public RateDto() {
    }

    public RateDto(String usd, double value) {
        this.name = usd;
        this.value = value;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
