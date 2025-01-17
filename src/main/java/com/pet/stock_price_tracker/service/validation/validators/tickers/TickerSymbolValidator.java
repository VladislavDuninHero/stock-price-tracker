package com.pet.stock_price_tracker.service.validation.validators.tickers;

import com.pet.stock_price_tracker.client.dto.polygon.request.TickerRequestDTO;
import com.pet.stock_price_tracker.service.validation.validators.BaseValidator;

public class TickerSymbolValidator extends BaseValidator<TickerRequestDTO> {
    @Override
    public TickerRequestDTO validate(TickerRequestDTO value) {


        if (getNext() != null) {
            return getNext().validate(value);
        }

        return value;
    }
}
