package org.example.softunifinalproject.init;

import org.example.softunifinalproject.repository.RateRepository;
import org.example.softunifinalproject.service.DatabaseRateStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DbInitInitialExchangeRate implements CommandLineRunner {
    private final RateRepository rateRepository;
    private final DatabaseRateStorageService databaseRateStorageService;

    public DbInitInitialExchangeRate(RateRepository rateRepository, DatabaseRateStorageService databaseRateStorageService) {
        this.rateRepository = rateRepository;
        this.databaseRateStorageService = databaseRateStorageService;
    }

    @Override
    public void run(String... args) throws Exception {
        if(rateRepository.count() == 0) {
            databaseRateStorageService.saveRates();
        }

    }
}
