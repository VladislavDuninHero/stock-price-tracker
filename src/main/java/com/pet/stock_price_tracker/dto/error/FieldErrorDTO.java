package com.pet.stock_price_tracker.dto.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldErrorDTO extends AbstractError {

    private String field;

    public FieldErrorDTO(
            String field,
            String message,
            String errorCode
    ) {
        super(errorCode, message);
        this.field = field;
    }
}
