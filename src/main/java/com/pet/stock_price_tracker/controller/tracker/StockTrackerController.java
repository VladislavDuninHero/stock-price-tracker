package com.pet.stock_price_tracker.controller.tracker;

import com.pet.stock_price_tracker.client.dto.polygon.request.TickerRequestDTO;
import com.pet.stock_price_tracker.client.dto.polygon.response.TickerDTO;
import com.pet.stock_price_tracker.constants.ExceptionMessage;
import com.pet.stock_price_tracker.constants.Message;
import com.pet.stock_price_tracker.constants.OfficialProperties;
import com.pet.stock_price_tracker.constants.Routes;
import com.pet.stock_price_tracker.dto.ticker.MessageDTO;
import com.pet.stock_price_tracker.enums.TickerSymbol;
import com.pet.stock_price_tracker.service.tracker.TickerService;
import com.pet.stock_price_tracker.service.validation.annotation.EnumValidate;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry;
import jakarta.validation.constraints.NotEmpty;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Routes.API_V1_TRACKER_ROUTE)
public class StockTrackerController {

    private final TickerService tickerService;
    private final MeterRegistry prometheusMeterRegistry;

    public StockTrackerController(final TickerService tickerService, final MeterRegistry prometheusMeterRegistry) {
        this.tickerService = tickerService;
        this.prometheusMeterRegistry = prometheusMeterRegistry;
    }

    @PostMapping(Routes.STOCK_TICKER_SAVE_ROUTE)
    public ResponseEntity<MessageDTO> save(@Validated @RequestBody TickerRequestDTO tickerRequestDTO) {
        tickerService.save(tickerRequestDTO);

        return ResponseEntity.ok(new MessageDTO(Message.SUCCESS_MESSAGE));
    }

    @GetMapping(Routes.STOCK_TICKER_GET_SAVED_ROUTE)
    public ResponseEntity<TickerDTO> getSaved(
            @RequestParam
            @NotEmpty(message = ExceptionMessage.SYMBOL_PARAM_IS_NOT_BE_EMPTY)
            @NonNull
            @EnumValidate(enumClass = TickerSymbol.class, message = ExceptionMessage.TICKER_NOT_FOUND_EXCEPTION)
            String symbol
    ) {
        TickerDTO tickerDTO = tickerService.getSavedTickers(symbol);

        prometheusMeterRegistry.counter(OfficialProperties.PROMETHEUS_SAVED_TICKERS_REQUESTS_COUNTER).increment();

        return ResponseEntity.ok(tickerDTO);
    }
}
