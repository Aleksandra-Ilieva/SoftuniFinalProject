package org.example.softunifinalproject.model.dto.exchangeRateResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class ExchangeRateResponse {
    private double eurRate;
    private double usdRate;

    @JsonProperty("conversion_rates")
    public void setConversionRates(Map<String, Double> conversionRates) {
        this.eurRate = conversionRates.get("EUR");
        this.usdRate = conversionRates.get("USD");
    }

    public double getEurRate() {
        return eurRate;
    }

    public void setEurRate(double eurRate) {
        this.eurRate = eurRate;
    }

    public double getUsdRate() {
        return usdRate;
    }

    public void setUsdRate(double usdRate) {
        this.usdRate = usdRate;
    }
}
