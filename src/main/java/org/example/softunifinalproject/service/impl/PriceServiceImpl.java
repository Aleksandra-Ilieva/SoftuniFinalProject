package org.example.softunifinalproject.service.impl;

import org.example.softunifinalproject.model.dto.PriceDto;
import org.example.softunifinalproject.model.entity.Price;
import org.example.softunifinalproject.repository.PriceRepository;
import org.example.softunifinalproject.service.PriceService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PriceServiceImpl implements PriceService {
    private final PriceRepository priceRepository;
    private final ModelMapper modelMapper;

    public PriceServiceImpl(PriceRepository priceRepository, ModelMapper modelMapper) {
        this.priceRepository = priceRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<PriceDto> getAllPrices() {
        List<Price> prices = (List<Price>) priceRepository.findAll();
        List<PriceDto> priceDtos = new ArrayList<>();
        for (Price price : prices) {
            PriceDto priceDto = modelMapper.map(price, PriceDto.class);
            priceDtos.add(priceDto);

        }
        return priceDtos;
    }
}
