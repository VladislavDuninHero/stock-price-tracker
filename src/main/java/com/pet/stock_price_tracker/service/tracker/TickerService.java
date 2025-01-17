package com.pet.stock_price_tracker.service.tracker;

import com.pet.stock_price_tracker.client.dto.polygon.request.TickerRequestDTO;
import com.pet.stock_price_tracker.client.dto.polygon.response.TickerDTO;

import java.util.List;

public interface TickerService {
    void save(TickerRequestDTO tickerRequestDTO);
    TickerDTO getSavedTickers(String symbol);
}
