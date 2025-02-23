package com.pet.stock_price_tracker.service.validation.validators.admin;

import com.pet.stock_price_tracker.constants.ExceptionMessage;
import com.pet.stock_price_tracker.dto.admin.AdminCreateUserDTO;
import com.pet.stock_price_tracker.dto.user.registration.UserDTO;
import com.pet.stock_price_tracker.exception.InvalidCharactersException;
import com.pet.stock_price_tracker.service.validation.validators.BaseValidator;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Order(1)
@Component
public class AdminCreateUserSpecialCharactersValidator extends BaseValidator<AdminCreateUserDTO> {

    private final Pattern pattern = Pattern.compile("[^\\d\\w]");

    @Override
    public AdminCreateUserDTO validate(AdminCreateUserDTO value) {

        Matcher loginMatcher = pattern.matcher(value.getLogin());
        Matcher passwordMatcher = pattern.matcher(value.getPassword());
        System.out.println(value.getLogin());

        if (loginMatcher.find() || passwordMatcher.find()) {
            throw new InvalidCharactersException(ExceptionMessage.INVALID_CHARACTERS_EXCEPTION);
        }

        if (getNext() != null) {
            return getNext().validate(value);
        }

        return value;
    }

}
