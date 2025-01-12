package com.pet.stock_price_tracker.controller;

import com.pet.stock_price_tracker.dto.error.UserErrorDTO;
import com.pet.stock_price_tracker.dto.error.ValidationErrorDTO;
import com.pet.stock_price_tracker.enums.ErrorCode;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MalformedJwtException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ValidationErrorDTO<UserErrorDTO> onMalformedJwtException(MalformedJwtException ex) {
        UserErrorDTO userErrorDTO = new UserErrorDTO(ErrorCode.INVALID_CREDENTIALS.name(), ex.getLocalizedMessage());

        return new ValidationErrorDTO<>(List.of(userErrorDTO));
    }
}
