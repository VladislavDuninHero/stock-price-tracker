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

import java.time.format.DateTimeFormatter;

@Service
public class PolygonIntegrationService implements PolygonService {

    private final Polygon polygon;
    private final PolygonIntegrationProperties polygonIntegrationProperties;

    public PolygonIntegrationService(Polygon polygon, PolygonIntegrationProperties polygonIntegrationProperties) {
        this.polygon = polygon;
        this.polygonIntegrationProperties = polygonIntegrationProperties;
    }

    @Override
    public TickerIntegrationResponseDTO save(TickerRequestDTO tickerRequestDTO) {

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        int multiplier =
                tickerRequestDTO.getEnd().getDayOfMonth() - tickerRequestDTO.getStart().getDayOfMonth() <= 0
                        ? 1
                        : tickerRequestDTO.getEnd().getDayOfMonth() - tickerRequestDTO.getStart().getDayOfMonth();
        String ticker = tickerRequestDTO.getTicker();
        String timespan = TimespanType.DAY.name().toLowerCase();
        String from = tickerRequestDTO.getStart().format(dateFormatter);
        String to = tickerRequestDTO.getEnd().format(dateFormatter);
        boolean adjusted = Boolean.TRUE;
        String sort = SortedType.ASC.name().toLowerCase();
        String apiKey = polygonIntegrationProperties.getApiKey();

        try {
            return polygon.save(
                    ticker,
                    multiplier,
                    timespan,
                    from,
                    to,
                    adjusted,
                    sort,
                    apiKey
            );
        } catch (Exception e) {
            throw new IntegrationException(e.getMessage());
        }
    }
}
