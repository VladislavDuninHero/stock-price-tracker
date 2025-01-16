package com.pet.stock_price_tracker.service.utils.mapping;

import com.pet.stock_price_tracker.client.dto.polygon.response.TickerDataIntegrationResponseDTO;
import com.pet.stock_price_tracker.entity.Ticker;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TickerMapper {

    @Mapping(source = "o", target = "open")
    @Mapping(source = "c", target = "close")
    @Mapping(source = "h", target = "highest")
    @Mapping(source = "l", target = "lowest")
    @Mapping(source = "t", target = "date")
    Ticker toEntity(TickerDataIntegrationResponseDTO tickerDataIntegrationResponseDTO);
}
