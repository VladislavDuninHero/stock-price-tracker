package com.pet.stock_price_tracker.client.polygon;

import com.pet.stock_price_tracker.client.dto.polygon.response.TickerDTO;
import com.pet.stock_price_tracker.constants.PolygonIntegrationEndpoint;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;

@FeignClient(name = "${feign.integration.polygon.name}", url = "${feign.integration.polygon-v2.url}")
public interface Polygon {

    @GetMapping(PolygonIntegrationEndpoint.GET_TICKERS_ENDPOINT)
    TickerDTO save(
            @PathVariable String ticker,
            @PathVariable int multiplier,
            @PathVariable String timespan,
            @PathVariable LocalDate from,
            @PathVariable LocalDate to
    );
}
