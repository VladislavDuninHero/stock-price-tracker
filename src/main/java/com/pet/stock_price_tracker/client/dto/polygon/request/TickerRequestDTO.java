package com.pet.stock_price_tracker.client.dto.polygon.request;

import com.pet.stock_price_tracker.constants.ExceptionMessage;
import com.pet.stock_price_tracker.enums.TickerSymbol;
import com.pet.stock_price_tracker.service.validation.annotation.EnumValidate;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TickerRequestDTO {

    @NotNull
    @NotEmpty
    @EnumValidate(enumClass = TickerSymbol.class, message = ExceptionMessage.TICKER_NOT_FOUND_EXCEPTION)
    private String ticker;

    @NotNull
    private LocalDate start;

    @NotNull
    private LocalDate end;
}
