package org.example.softunifinalproject.controller;

import org.example.softunifinalproject.model.dto.PriceDto;
import org.example.softunifinalproject.model.entity.Price;
import org.example.softunifinalproject.repository.PriceRepository;
import org.example.softunifinalproject.service.PriceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class PriceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private PriceService priceService;

    @BeforeEach
    public void setup() {
        priceRepository.deleteAll();

        // Set up test prices
        Price price1 = new Price();
        price1.setProcedureType("Consultation");
        price1.setPrice(100);
        priceRepository.save(price1);

        Price price2 = new Price();
        price2.setProcedureType("Checkup");
        price2.setPrice(50);
        priceRepository.save(price2);

        Price price3 = new Price();
        price3.setProcedureType("Consultation - Urgent");
        price3.setPrice(150);
        priceRepository.save(price3);
    }

    @Test
    public void testGetPricesPage() throws Exception {
        mockMvc.perform(get("/prices")
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("prices"))
                .andExpect(model().attributeExists("prices"));


        List<PriceDto> priceDtos = priceService.getAllPrices();


        List<String> sortedProcedureTypes = priceDtos.stream()
                .map(PriceDto::getProcedureType)
                .collect(Collectors.toList());


        String[] expectedOrder = {"Consultation","Checkup" ,"Consultation - Urgent"};


        assertEquals(expectedOrder.length, sortedProcedureTypes.size(), "Size of sortedProcedureTypes does not match");


        for (int i = 0; i < expectedOrder.length; i++) {
            assertEquals(expectedOrder[i], sortedProcedureTypes.get(i), "Procedure types are not in the expected order");
        }
    }
}



