package com.pet.stock_price_tracker.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pet.stock_price_tracker.client.dto.polygon.request.TickerRequestDTO;
import com.pet.stock_price_tracker.client.dto.polygon.response.TickerDTO;
import com.pet.stock_price_tracker.constants.RequestParam;
import com.pet.stock_price_tracker.constants.Routes;
import com.pet.stock_price_tracker.dto.user.registration.UserDTO;
import com.pet.stock_price_tracker.enums.TickerSymbol;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("component-test")
@AutoConfigureMockMvc
public class TrackerControllerComponentTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @WithMockUser(username = "savetickeruserlogin")
    public void tracker_SaveTickersWithValidData_ReturnsValidEntity() throws Exception {
        //given
        UserDTO userDTO = new UserDTO(
                "savetickeruserlogin",
                "savetickeruserpass"
        );
        String user = objectMapper.writeValueAsString(userDTO);
        var userRegistrationBuilder = MockMvcRequestBuilders
                .post(Routes.API_USER_REGISTRATION_ROUTE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(user);
        mockMvc.perform(userRegistrationBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        TickerRequestDTO tickerRequestDTO = new TickerRequestDTO(
                TickerSymbol.AAPL.name(),
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 2, 1)
        );
        String json = objectMapper.writeValueAsString(tickerRequestDTO);
        var tickerRequestBuilder = MockMvcRequestBuilders
                .post(Routes.API_V1_TRACKER_ROUTE + Routes.STOCK_TICKER_SAVE_ROUTE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        //when
        mockMvc.perform(tickerRequestBuilder)
                //then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(username = "savedtickeruserlogin")
    public void tracker_GetSavedTickers_ReturnsValidEntity() throws Exception {
        //given
        UserDTO userDTO = new UserDTO(
                "savedtickeruserlogin",
                "savedtickeruserpass"
        );
        String user = objectMapper.writeValueAsString(userDTO);
        var userRegistrationBuilder = MockMvcRequestBuilders
                .post(Routes.API_USER_REGISTRATION_ROUTE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(user);
        mockMvc.perform(userRegistrationBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        TickerRequestDTO tickerRequestDTO = new TickerRequestDTO(
                TickerSymbol.AAPL.name(),
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 2, 1)
        );
        String json = objectMapper.writeValueAsString(tickerRequestDTO);
        var tickerRequestBuilder = MockMvcRequestBuilders
                .post(Routes.API_V1_TRACKER_ROUTE + Routes.STOCK_TICKER_SAVE_ROUTE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(tickerRequestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
        //when
        var getSavedTickersRequestBuilder = MockMvcRequestBuilders
                .get(Routes.API_V1_TRACKER_ROUTE + Routes.STOCK_TICKER_GET_SAVED_ROUTE)
                .param(RequestParam.SYMBOL, TickerSymbol.AAPL.name())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult tickersRequestResult = mockMvc.perform(getSavedTickersRequestBuilder)
                //then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        TickerDTO tickers = objectMapper.readValue(
                tickersRequestResult.getResponse().getContentAsString(),
                TickerDTO.class
        );

        Assertions.assertFalse(tickers.getData().isEmpty());
    }
}
