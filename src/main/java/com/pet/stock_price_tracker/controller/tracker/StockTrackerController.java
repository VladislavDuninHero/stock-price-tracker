package com.pet.stock_price_tracker.controller.tracker;

import com.pet.stock_price_tracker.client.dto.polygon.request.TickerRequestDTO;
import com.pet.stock_price_tracker.client.dto.polygon.response.TickerDTO;
import com.pet.stock_price_tracker.constants.Message;
import com.pet.stock_price_tracker.constants.Routes;
import com.pet.stock_price_tracker.dto.ticker.MessageDTO;
import com.pet.stock_price_tracker.service.tracker.TickerService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Routes.API_V1_TRACKER_ROUTE)
public class StockTrackerController {

    private final TickerService tickerService;

    public StockTrackerController(final TickerService tickerService) {
        this.tickerService = tickerService;
    }

    @PostMapping(Routes.STOCK_TICKER_SAVE_ROUTE)
    public ResponseEntity<MessageDTO> save(@Validated @RequestBody TickerRequestDTO tickerRequestDTO) {
        tickerService.save(tickerRequestDTO);

        return ResponseEntity.ok(new MessageDTO(Message.SUCCESS_MESSAGE));
    }

    @GetMapping(Routes.STOCK_TICKER_GET_SAVED_ROUTE)
    public ResponseEntity<TickerDTO> getSaved(@RequestParam String symbol) {
        TickerDTO tickerDTO = tickerService.getSavedTickers(symbol);

        return ResponseEntity.ok(tickerDTO);
    }
}
