package org.example.softunifinalproject.controller;

import org.example.softunifinalproject.model.dto.RateDto;
import org.example.softunifinalproject.service.DatabaseRateStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExchangeRateRestController {


    private final DatabaseRateStorageService databaseRateStorageService;

    public ExchangeRateRestController(DatabaseRateStorageService databaseRateStorageService) {
        this.databaseRateStorageService = databaseRateStorageService;
    }


    @GetMapping("/rates")
    public ResponseEntity<RateDto> getAllRates(){
     List<RateDto> rateDtos=   this.databaseRateStorageService.getRatesFromDB();
     if(rateDtos.isEmpty()){
         return ResponseEntity.noContent().build();
     }
        for (RateDto rateDto : rateDtos) {
            if(rateDto.getName().equals("EUR")){
                return ResponseEntity.ok(rateDto);
            }

        }
     return null;
    }
}
