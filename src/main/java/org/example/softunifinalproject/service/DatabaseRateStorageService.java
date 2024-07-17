package org.example.softunifinalproject.service;

import org.example.softunifinalproject.model.dto.RateDto;

import java.util.List;

public interface DatabaseRateStorageService {
    void saveRates();

    List<RateDto> getRatesFromDB();
}
