package org.example.softunifinalproject.service.impl;

import org.example.softunifinalproject.model.dto.RateDto;
import org.example.softunifinalproject.model.entity.Rate;
import org.example.softunifinalproject.repository.RateRepository;
import org.example.softunifinalproject.service.ExchangeRateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DatabaseRateStorageServiceImplTest {

    @Mock
    private RateRepository rateRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ExchangeRateService exchangeRateService;

    @InjectMocks
    private DatabaseRateStorageServiceImpl databaseRateStorageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveRates() {
        // Arrange
        Rate rate = new Rate();
        rate.setCreatedOn(LocalDateTime.now());
        List<Rate> rates = Arrays.asList(rate);

        when(exchangeRateService.getRates()).thenReturn(rates);

        // Act
        databaseRateStorageService.saveRates();

        // Assert
        verify(exchangeRateService).getRates();
        verify(rateRepository).saveAll(anyList());
    }

    @Test
    void testGetRatesFromDB() {
        // Arrange
        Rate rate = new Rate();
        rate.setCreatedOn(LocalDateTime.now());
        RateDto rateDto = new RateDto();

        when(rateRepository.findAll()).thenReturn(Arrays.asList(rate));
        when(modelMapper.map(any(Rate.class), eq(RateDto.class))).thenReturn(rateDto);

        // Act
        List<RateDto> result = databaseRateStorageService.getRatesFromDB();

        // Assert
        assertEquals(1, result.size(), "The size of the result list should be 1");
        assertEquals(rateDto, result.get(0), "The rateDto should match the expected value");
        verify(rateRepository).findAll();
        verify(modelMapper).map(any(Rate.class), eq(RateDto.class));
    }

}