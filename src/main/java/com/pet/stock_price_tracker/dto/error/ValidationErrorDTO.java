package com.pet.stock_price_tracker.dto.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ValidationErrorDTO<T extends AbstractError> {
    private List<T> errors;
}
