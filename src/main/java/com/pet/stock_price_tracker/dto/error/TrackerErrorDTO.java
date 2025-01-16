package com.pet.stock_price_tracker.dto.error;

public class TrackerErrorDTO extends AbstractError {
    public TrackerErrorDTO(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
}
