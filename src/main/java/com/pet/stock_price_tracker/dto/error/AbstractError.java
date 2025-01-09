package com.pet.stock_price_tracker.dto.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class AbstractError {
    public String errorCode;
    public String errorMessage;
}
