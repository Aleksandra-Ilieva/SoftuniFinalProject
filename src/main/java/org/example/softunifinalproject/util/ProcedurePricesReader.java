package org.example.softunifinalproject.util;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.example.softunifinalproject.model.entity.Price;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProcedurePricesReader {
    String csvFile = "src/main/resources/procedure-prices.csv";

    public List<Price> readPricesFromCSV() {
        List<Price> prices = new ArrayList<>();
        int counter = 0;


        try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
            List<String[]> records = reader.readAll();


            for (String[] record : records) {
                counter++;
                if(counter == 1) {
                    continue;
                }
                String procedureType = record[0];
                double price = Double.parseDouble(record[1]);

                Price priceObj = new Price();
                priceObj.setProcedureType(procedureType);
                priceObj.setPrice(price);

                prices.add(priceObj);
            }

        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        return prices;
    }
}
