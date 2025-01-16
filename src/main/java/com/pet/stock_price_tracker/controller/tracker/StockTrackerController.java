package com.pet.stock_price_tracker.controller.tracker;

import com.pet.stock_price_tracker.client.dto.polygon.request.TickerRequestDTO;
import com.pet.stock_price_tracker.constants.Routes;
import com.pet.stock_price_tracker.service.tracker.TickerService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/tracker")
public class StockTrackerController {

    private final TickerService tickerService;

    public StockTrackerController(final TickerService tickerService) {
        this.tickerService = tickerService;
    }

    @PostMapping(Routes.STOCK_TICKER_SAVE_ROUTE)
    public ResponseEntity<Void> save(@Validated @RequestBody TickerRequestDTO tickerRequestDTO) {
        tickerService.save(tickerRequestDTO);

        return ResponseEntity.created(URI.create("")).build();
    }

}
