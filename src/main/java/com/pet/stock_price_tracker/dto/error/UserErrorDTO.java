package com.pet.stock_price_tracker.dto.error;

public class UserErrorDTO extends AbstractError{

    public UserErrorDTO(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

}
