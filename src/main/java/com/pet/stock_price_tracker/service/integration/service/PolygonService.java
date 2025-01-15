package com.pet.stock_price_tracker.service.integration.service;

import com.pet.stock_price_tracker.client.dto.polygon.request.TickerRequestDTO;
import com.pet.stock_price_tracker.client.dto.polygon.response.TickerDTO;

public interface PolygonService {
    TickerDTO save(TickerRequestDTO tickerRequestDTO);
}
