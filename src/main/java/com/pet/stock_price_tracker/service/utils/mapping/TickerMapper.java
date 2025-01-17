package com.pet.stock_price_tracker.service.utils.mapping;

import com.pet.stock_price_tracker.client.dto.polygon.response.TickerDataDTO;
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

    @Mapping(source = "open", target = "open")
    @Mapping(source = "close", target = "close")
    @Mapping(source = "highest", target = "highest")
    @Mapping(source = "lowest", target = "lowest")
    @Mapping(source = "date", target = "date")
    TickerDataDTO toTickerDataDTO(Ticker ticker);
}
