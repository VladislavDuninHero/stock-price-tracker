package com.pet.stock_price_tracker.controller.admin;

import com.pet.stock_price_tracker.controller.user.UserController;
import com.pet.stock_price_tracker.dto.error.FieldErrorDTO;
import com.pet.stock_price_tracker.dto.error.UserErrorDTO;
import com.pet.stock_price_tracker.dto.error.ValidationErrorDTO;
import com.pet.stock_price_tracker.enums.ErrorCode;
import com.pet.stock_price_tracker.exception.InvalidCharactersException;
import com.pet.stock_price_tracker.exception.RoleValidationException;
import com.pet.stock_price_tracker.exception.SpaceSymbolException;
import com.pet.stock_price_tracker.exception.UserIsAlreadyRegisteredException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice(basePackageClasses = AdminController.class)
public class AdminControllerExceptionHandler {

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
        UserErrorDTO userErrorDTO = new UserErrorDTO(
                ErrorCode.USER_IS_ALREADY_EXISTS.name(),
                ex.getLocalizedMessage()
        );

        return new ValidationErrorDTO<>(List.of(userErrorDTO));
    }

    @ExceptionHandler(RoleValidationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorDTO<UserErrorDTO> onUserIsAlreadyRegistered(RoleValidationException ex) {
        UserErrorDTO userErrorDTO = new UserErrorDTO(
                ErrorCode.USER_IS_ALREADY_EXISTS.name(),
                ex.getLocalizedMessage()
        );

        return new ValidationErrorDTO<>(List.of(userErrorDTO));
    }

    @ExceptionHandler(InvalidCharactersException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorDTO<UserErrorDTO> onUserIsAlreadyRegistered(InvalidCharactersException ex) {
        UserErrorDTO userErrorDTO = new UserErrorDTO(
                ErrorCode.USER_IS_ALREADY_EXISTS.name(),
                ex.getLocalizedMessage()
        );

        return new ValidationErrorDTO<>(List.of(userErrorDTO));
    }

    @ExceptionHandler(SpaceSymbolException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorDTO<UserErrorDTO> onUserIsAlreadyRegistered(SpaceSymbolException ex) {
        UserErrorDTO userErrorDTO = new UserErrorDTO(
                ErrorCode.USER_IS_ALREADY_EXISTS.name(),
                ex.getLocalizedMessage()
        );

        return new ValidationErrorDTO<>(List.of(userErrorDTO));
    }
}
