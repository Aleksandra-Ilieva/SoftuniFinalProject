package org.example.softunifinalproject.controller;

import org.example.softunifinalproject.model.dto.PriceDto;
import org.example.softunifinalproject.service.PriceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PriceController {
    private final PriceService priceService;


    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping("/prices")
    public String price(Model model){
        List<PriceDto> prices = this.priceService.getAllPrices();
        List<PriceDto> sortedPrices = prices.stream()
                .sorted(Comparator.comparing((PriceDto priceDto) -> !priceDto.getProcedureType().startsWith("Consultation"))
                        .thenComparing(PriceDto::getProcedureType))
                .collect(Collectors.toList());
        model.addAttribute("prices", sortedPrices);
        return "prices";
    }
}
