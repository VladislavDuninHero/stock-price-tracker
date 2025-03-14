package com.pet.stock_price_tracker.controller.tracker;

import com.pet.stock_price_tracker.dto.error.FieldErrorDTO;
import com.pet.stock_price_tracker.dto.error.ParamsErrorDTO;
import com.pet.stock_price_tracker.dto.error.TrackerErrorDTO;
import com.pet.stock_price_tracker.dto.error.ValidationErrorDTO;
import com.pet.stock_price_tracker.enums.ErrorCode;
import com.pet.stock_price_tracker.exception.DateValidationException;
import com.pet.stock_price_tracker.exception.IntegrationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.List;

@RestControllerAdvice(basePackageClasses = StockTrackerController.class)
public class StockTrackerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorDTO<FieldErrorDTO> onMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        final List<FieldErrorDTO> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> new FieldErrorDTO(
                                error.getField(),
                                error.getDefaultMessage(),
                                ErrorCode.VALIDATION_ERROR.name()
                        )
                )
                .toList();

        return new ValidationErrorDTO<>(errors);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorDTO<ParamsErrorDTO> onHandlerMethodValidationException(HandlerMethodValidationException ex) {
        final List<ParamsErrorDTO> errors = ex.getAllErrors()
                .stream()
                .map(error -> new ParamsErrorDTO(ErrorCode.VALIDATION_ERROR.name(),  error.getDefaultMessage()))
                .toList();

        return new ValidationErrorDTO<>(errors);
    }

    @ExceptionHandler(IntegrationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ValidationErrorDTO<TrackerErrorDTO> onIntegrationException(IntegrationException ex) {
        TrackerErrorDTO trackerErrorDTO = new TrackerErrorDTO(ErrorCode.INTEGRATION_REQUEST_ERROR.name(), ex.getMessage());

        return new ValidationErrorDTO<>(List.of(trackerErrorDTO));
    }

    @ExceptionHandler(DateValidationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorDTO<TrackerErrorDTO> onDateValidationException(DateValidationException ex) {
        TrackerErrorDTO trackerErrorDTO = new TrackerErrorDTO(ErrorCode.VALIDATION_ERROR.name(), ex.getMessage());

        return new ValidationErrorDTO<>(List.of(trackerErrorDTO));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorDTO<TrackerErrorDTO> onMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        TrackerErrorDTO trackerErrorDTO = new TrackerErrorDTO(ErrorCode.INVALID_PARAMETERS_ERROR.name(), ex.getMessage());

        return new ValidationErrorDTO<>(List.of(trackerErrorDTO));
    }
}
