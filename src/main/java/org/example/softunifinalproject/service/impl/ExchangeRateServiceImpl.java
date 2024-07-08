package org.example.softunifinalproject.service.impl;

import org.example.softunifinalproject.model.dto.RateDto;
import org.example.softunifinalproject.model.dto.exchangeRateResponse.ExchangeRateResponse;
import org.example.softunifinalproject.model.entity.Rate;
import org.example.softunifinalproject.repository.RateRepository;
import org.example.softunifinalproject.service.ExchangeRateService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {


    private final RestTemplate restTemplate;
    private final String EXCHANGE_RATE_API_KEY = System.getenv("EXCHANGE_RATE_API_KEY");
    public ExchangeRateServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Rate> getRates() {
        String url = "https://v6.exchangerate-api.com/v6/" + EXCHANGE_RATE_API_KEY + "/latest/USD";
        ExchangeRateResponse response = restTemplate.getForObject(url, ExchangeRateResponse.class);

        Rate rateEuro = new Rate();
        rateEuro.setValue(response.getEurRate());
        rateEuro.setName("EUR");

        Rate rateUSD = new Rate();
        rateUSD.setValue(response.getUsdRate());
        rateUSD.setName("USD");

        List<Rate> rates = new ArrayList<>();
        rates.add(rateEuro);
        rates.add(rateUSD);
        return rates;
    }


}
