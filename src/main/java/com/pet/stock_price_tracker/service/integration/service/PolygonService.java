package com.pet.stock_price_tracker.service.integration.service;

import com.pet.stock_price_tracker.client.dto.polygon.request.TickerRequestDTO;
import com.pet.stock_price_tracker.client.dto.polygon.response.TickerIntegrationResponseDTO;

public interface PolygonService {
    TickerIntegrationResponseDTO save(TickerRequestDTO tickerRequestDTO);
}
