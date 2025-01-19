package com.pet.stock_price_tracker.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pet.stock_price_tracker.client.dto.polygon.request.TickerRequestDTO;
import com.pet.stock_price_tracker.constants.Routes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

@SpringBootTest
@ActiveProfiles("component-test")
@AutoConfigureMockMvc
public class TrackerControllerComponentTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    public void tracker_SaveTickersWithValidData_ReturnsValidEntity() throws Exception {
        //given
        TickerRequestDTO tickerRequestDTO = new TickerRequestDTO(
                "AAPL",
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2024, 1, 1)
        );
        String json = objectMapper.writeValueAsString(tickerRequestDTO);
        var builder = MockMvcRequestBuilders
                .post(Routes.API_V1_TRACKER_ROUTE + Routes.STOCK_TICKER_SAVE_ROUTE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        //when
        mockMvc.perform(builder)
                //then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }
}
