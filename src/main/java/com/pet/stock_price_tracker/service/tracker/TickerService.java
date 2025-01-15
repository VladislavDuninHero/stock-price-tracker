package com.pet.stock_price_tracker.service.tracker;

import com.pet.stock_price_tracker.client.dto.polygon.request.TickerRequestDTO;

public interface TickerService {
    public void save(TickerRequestDTO ticker);
}
