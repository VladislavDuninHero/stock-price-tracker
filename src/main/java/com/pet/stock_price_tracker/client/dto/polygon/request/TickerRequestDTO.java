package com.pet.stock_price_tracker.client.dto.polygon.request;

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
    private String ticker;

    @NotNull
    private LocalDate start;

    @NotNull
    private LocalDate end;
}
