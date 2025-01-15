package com.pet.stock_price_tracker.service.tracker.impl;

import com.pet.stock_price_tracker.client.dto.polygon.request.TickerRequestDTO;
import com.pet.stock_price_tracker.client.dto.polygon.response.TickerDTO;
import com.pet.stock_price_tracker.client.dto.polygon.response.TickerDataDTO;
import com.pet.stock_price_tracker.entity.Ticker;
import com.pet.stock_price_tracker.repository.TickerRepository;
import com.pet.stock_price_tracker.service.integration.service.PolygonService;
import com.pet.stock_price_tracker.service.tracker.TickerService;
import org.springframework.stereotype.Service;

@Service
public class TickerServiceImpl implements TickerService {

    private final TickerRepository tickerRepository;
    private final PolygonService polygonService;

    public TickerServiceImpl(TickerRepository tickerRepository, PolygonService polygonService) {
        this.tickerRepository = tickerRepository;
        this.polygonService = polygonService;
    }

    @Override
    public void save(TickerRequestDTO ticker) {
        TickerDTO tickerDTO = polygonService.save(ticker);

        tickerRepository.save(new Ticker());
    }
}
