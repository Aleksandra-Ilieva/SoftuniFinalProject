package org.example.softunifinalproject.service.impl;

import org.example.softunifinalproject.model.dto.PriceDto;
import org.example.softunifinalproject.model.entity.Price;
import org.example.softunifinalproject.repository.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PriceServiceImplUnitTest {

    @Mock
    private PriceRepository priceRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PriceServiceImpl priceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPrices() {
        List<Price> prices = new ArrayList<>();
        Price price1 = new Price();
        Price price2 = new Price();
        prices.add(price1);
        prices.add(price2);

        when(priceRepository.findAll()).thenReturn(prices);
        when(modelMapper.map(price1, PriceDto.class)).thenReturn(new PriceDto());
        when(modelMapper.map(price2, PriceDto.class)).thenReturn(new PriceDto());

        List<PriceDto> result = priceService.getAllPrices();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(priceRepository, times(1)).findAll();
        verify(modelMapper, times(1)).map(price1, PriceDto.class);
        verify(modelMapper, times(1)).map(price2, PriceDto.class);
    }

}