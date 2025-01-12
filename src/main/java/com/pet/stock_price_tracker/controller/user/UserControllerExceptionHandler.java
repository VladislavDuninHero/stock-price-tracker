package com.pet.stock_price_tracker.controller.user;

import com.pet.stock_price_tracker.dto.error.FieldErrorDTO;
import com.pet.stock_price_tracker.dto.error.UserErrorDTO;
import com.pet.stock_price_tracker.dto.error.ValidationErrorDTO;
import com.pet.stock_price_tracker.enums.ErrorCode;
import com.pet.stock_price_tracker.exception.UserIsAlreadyRegisteredException;
import com.pet.stock_price_tracker.exception.UserNotFoundException;
import com.pet.stock_price_tracker.exception.UserPasswordIsInvalid;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice(basePackageClasses = UserController.class)
public class UserControllerExceptionHandler {

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

    @ExceptionHandler(UserIsAlreadyRegisteredException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorDTO<UserErrorDTO> onUserIsAlreadyRegistered(UserIsAlreadyRegisteredException ex) {
        UserErrorDTO userErrorDTO = new UserErrorDTO(ErrorCode.USER_IS_ALREADY_EXISTS.name(), ex.getLocalizedMessage());

        return new ValidationErrorDTO<>(List.of(userErrorDTO));
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorDTO<UserErrorDTO> onUserNotFound(UserNotFoundException ex) {
        UserErrorDTO userErrorDTO = new UserErrorDTO(ErrorCode.USER_NOT_FOUND.name(), ex.getLocalizedMessage());

        return new ValidationErrorDTO<>(List.of(userErrorDTO));
    }

    @ExceptionHandler(UserPasswordIsInvalid.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorDTO<UserErrorDTO> onUserPasswordIsInvalid(UserPasswordIsInvalid ex) {
        UserErrorDTO userErrorDTO = new UserErrorDTO(ErrorCode.INVALID_CREDENTIALS.name(), ex.getLocalizedMessage());

        return new ValidationErrorDTO<>(List.of(userErrorDTO));
    }
}
