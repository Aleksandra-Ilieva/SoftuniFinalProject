package org.example.softunifinalproject.service.impl;

import org.example.softunifinalproject.model.dto.RateDto;
import org.example.softunifinalproject.model.entity.Rate;
import org.example.softunifinalproject.repository.RateRepository;
import org.example.softunifinalproject.service.DatabaseRateStorageService;
import org.example.softunifinalproject.service.ExchangeRateService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseRateStorageServiceImpl implements DatabaseRateStorageService {
    private final RateRepository rateRepository;
    private final ModelMapper modelMapper;
    private final ExchangeRateService exchangeRateService;

    public DatabaseRateStorageServiceImpl(RateRepository rateRepository, ModelMapper modelMapper, ExchangeRateService exchangeRateService) {
        this.rateRepository = rateRepository;
        this.modelMapper = modelMapper;
        this.exchangeRateService = exchangeRateService;
    }

    @Override
    public void saveRates() {
        List<Rate> rates = exchangeRateService.getRates();
        for (Rate rate : rates) {
            rate.setCreatedOn(LocalDateTime.now());
        }
        rateRepository.saveAll(rates);

    }

    @Override
    public List<RateDto> getRatesFromDB() {
        List<RateDto> rates = new ArrayList<>();
        for (Rate rate : this.rateRepository.findAll()) {
            RateDto rateDto = modelMapper.map(rate, RateDto.class);
            rates.add(rateDto);

        }
        return rates;
    }
}
