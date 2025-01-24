package com.pet.stock_price_tracker.service.integration.service.impl;

import com.pet.stock_price_tracker.client.dto.polygon.request.TickerRequestDTO;
import com.pet.stock_price_tracker.client.dto.polygon.response.TickerIntegrationResponseDTO;
import com.pet.stock_price_tracker.client.polygon.Polygon;
import com.pet.stock_price_tracker.enums.SortedType;
import com.pet.stock_price_tracker.enums.TimespanType;
import com.pet.stock_price_tracker.exception.IntegrationException;
import com.pet.stock_price_tracker.properties.PolygonIntegrationProperties;
import com.pet.stock_price_tracker.service.integration.service.PolygonService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class PolygonIntegrationService implements PolygonService {

    private final Polygon polygon;
    private final PolygonIntegrationProperties polygonIntegrationProperties;

    private static final int MIN_DAY_COUNT = 0;

    public PolygonIntegrationService(Polygon polygon, PolygonIntegrationProperties polygonIntegrationProperties) {
        this.polygon = polygon;
        this.polygonIntegrationProperties = polygonIntegrationProperties;
    }

    @Override
    public TickerIntegrationResponseDTO save(TickerRequestDTO tickerRequestDTO) {

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        int multiplier = dateCalculator(tickerRequestDTO.getStart(), tickerRequestDTO.getEnd());
        String ticker = tickerRequestDTO.getTicker();
        String timespan = TimespanType.DAY.name().toLowerCase();
        String from = tickerRequestDTO.getStart().format(dateFormatter);
        String to = tickerRequestDTO.getEnd().format(dateFormatter);
        boolean adjusted = Boolean.TRUE;
        String sort = SortedType.ASC.name().toLowerCase();
        String apiKey = polygonIntegrationProperties.getApiKey();

        try {
            return polygon.save(ticker, multiplier, timespan, from, to, adjusted, sort, apiKey);
        } catch (Exception e) {
            throw new IntegrationException(e.getMessage());
        }
    }

    private int dateCalculator(LocalDate start, LocalDate end) {
        return end.getDayOfMonth() - start.getDayOfMonth() <= MIN_DAY_COUNT
                ? 1
                : end.getDayOfMonth() - start.getDayOfMonth();
    }
}
