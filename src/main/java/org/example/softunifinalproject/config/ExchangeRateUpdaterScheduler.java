package org.example.softunifinalproject.config;

import org.example.softunifinalproject.repository.RateRepository;
import org.example.softunifinalproject.service.DatabaseRateStorageService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ExchangeRateUpdaterScheduler {
    // TODO тествай дали работи

    private final DatabaseRateStorageService databaseRateStorageService;
    private final RateRepository rateRepository;

    public ExchangeRateUpdaterScheduler(DatabaseRateStorageService databaseRateStorageService, RateRepository rateRepository) {
        this.databaseRateStorageService = databaseRateStorageService;
        this.rateRepository = rateRepository;
    }

    @Scheduled(cron = "0 0 12 * * *") // Изпълнява се в 12:00 часа всеки ден
    public void updateExchangeRate() {
        rateRepository.deleteAll();
        databaseRateStorageService.saveRates();
    }
}
