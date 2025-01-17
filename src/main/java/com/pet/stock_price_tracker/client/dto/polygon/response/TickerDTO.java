package com.pet.stock_price_tracker.client.dto.polygon.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TickerDTO {
    private String ticker;
    private List<TickerDataDTO> data;
}
