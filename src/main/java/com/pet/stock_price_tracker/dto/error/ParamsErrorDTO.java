package com.pet.stock_price_tracker.dto.error;

public class ParamsErrorDTO extends AbstractError{
    public ParamsErrorDTO(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
}
