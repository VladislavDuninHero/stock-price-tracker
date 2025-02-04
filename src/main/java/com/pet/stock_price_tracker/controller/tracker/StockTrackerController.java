package com.pet.stock_price_tracker.controller.tracker;

import com.pet.stock_price_tracker.client.dto.polygon.request.TickerRequestDTO;
import com.pet.stock_price_tracker.client.dto.polygon.response.TickerDTO;
import com.pet.stock_price_tracker.constants.ExceptionMessage;
import com.pet.stock_price_tracker.constants.Message;
import com.pet.stock_price_tracker.constants.Routes;
import com.pet.stock_price_tracker.dto.ticker.MessageDTO;
import com.pet.stock_price_tracker.enums.TickerSymbol;
import com.pet.stock_price_tracker.service.tracker.TickerService;
import com.pet.stock_price_tracker.service.validation.annotation.EnumValidate;
import jakarta.validation.constraints.NotEmpty;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('SAVE_TICKERS_PERMISSION')")
    public ResponseEntity<MessageDTO> save(@Validated @RequestBody TickerRequestDTO tickerRequestDTO) {
        tickerService.save(tickerRequestDTO);

        return ResponseEntity.ok(new MessageDTO(Message.SUCCESS_MESSAGE));
    }

    @GetMapping(Routes.STOCK_TICKER_GET_SAVED_ROUTE)
    @PreAuthorize("hasAuthority('GET_TICKERS_PERMISSION')")
    public ResponseEntity<TickerDTO> getSaved(
            @RequestParam
            @NotEmpty(message = ExceptionMessage.SYMBOL_PARAM_IS_NOT_BE_EMPTY)
            @NonNull
            @EnumValidate(enumClass = TickerSymbol.class, message = ExceptionMessage.TICKER_NOT_FOUND_EXCEPTION)
            String symbol
    ) {
        TickerDTO tickerDTO = tickerService.getSavedTickers(symbol);

        return ResponseEntity.ok(tickerDTO);
    }
}
