package com.pet.stock_price_tracker.service.validation.validators.tickers;

import com.pet.stock_price_tracker.client.dto.polygon.request.TickerRequestDTO;
import com.pet.stock_price_tracker.constants.ExceptionMessage;
import com.pet.stock_price_tracker.exception.DateValidationException;
import com.pet.stock_price_tracker.service.validation.validators.BaseValidator;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Component
public class TickerRequestDateValidator extends BaseValidator<TickerRequestDTO> {

    @Override
    public TickerRequestDTO validate(TickerRequestDTO value) {
        if (value.getEnd().isBefore(value.getStart())) {
            throw new DateValidationException(ExceptionMessage.END_BEFORE_START_DATE_EXCEPTION);
        }

        if (getNext() != null) {
            return getNext().validate(value);
        }

        return value;
    }
}
