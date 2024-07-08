package org.example.softunifinalproject.init;

import org.example.softunifinalproject.model.entity.Price;
import org.example.softunifinalproject.repository.PriceRepository;
import org.example.softunifinalproject.util.ProcedurePricesReader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class DbInitProcedurePrices implements CommandLineRunner {

    private final PriceRepository priceRepository;

    public DbInitProcedurePrices(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(priceRepository.count() == 0) {
            ProcedurePricesReader procedurePricesReader = new ProcedurePricesReader();
            List<Price> procedurePrices = procedurePricesReader.readPricesFromCSV();
            for (Price price : procedurePrices) {
                priceRepository.save(price);
            }

        }



    }
}
