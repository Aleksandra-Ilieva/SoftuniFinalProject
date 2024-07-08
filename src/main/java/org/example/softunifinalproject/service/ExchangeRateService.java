package org.example.softunifinalproject.service;

import org.example.softunifinalproject.model.dto.RateDto;
import org.example.softunifinalproject.model.entity.Rate;

import java.util.List;

public interface ExchangeRateService {

    List<Rate> getRates();

}
