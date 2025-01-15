package com.pet.stock_price_tracker.service.integration.service.impl;

import com.pet.stock_price_tracker.client.dto.polygon.request.TickerRequestDTO;
import com.pet.stock_price_tracker.client.dto.polygon.response.TickerDTO;
import com.pet.stock_price_tracker.client.polygon.Polygon;
import com.pet.stock_price_tracker.enums.TimespanType;
import com.pet.stock_price_tracker.service.integration.service.PolygonService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PolygonIntegrationService implements PolygonService {

    private final Polygon polygon;

    public PolygonIntegrationService(Polygon polygon) {
        this.polygon = polygon;
    }

    @Override
    public TickerDTO save(TickerRequestDTO tickerRequestDTO) {
        int multiplier = tickerRequestDTO.getEnd().getDayOfMonth() - tickerRequestDTO.getStart().getDayOfMonth();
        String ticker = tickerRequestDTO.getTicker();
        String timespan = TimespanType.DAY.name().toLowerCase();
        LocalDate from = tickerRequestDTO.getStart();
        LocalDate to = tickerRequestDTO.getEnd();

        return polygon.save(
                ticker,
                multiplier,
                timespan,
                from,
                to
        );
    }
}
