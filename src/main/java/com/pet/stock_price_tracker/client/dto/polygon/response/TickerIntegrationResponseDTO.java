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
public class TickerIntegrationResponseDTO {
    private String ticker;
    private Integer queryCount;
    private Integer resultsCount;
    private Boolean adjusted;
    private List<TickerDataIntegrationResponseDTO> results;
    private String status;
    private String request_id;
    private Integer count;
}
