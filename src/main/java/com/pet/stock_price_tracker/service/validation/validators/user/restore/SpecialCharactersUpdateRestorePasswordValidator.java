package com.pet.stock_price_tracker.service.validation.validators.user.restore;

import com.pet.stock_price_tracker.constants.ExceptionMessage;
import com.pet.stock_price_tracker.dto.user.restore.UpdateRestorePasswordDTO;
import com.pet.stock_price_tracker.exception.InvalidCharactersException;
import com.pet.stock_price_tracker.service.validation.validators.BaseValidator;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Order(1)
@Component
public class SpecialCharactersUpdateRestorePasswordValidator extends BaseValidator<UpdateRestorePasswordDTO> {

    private final Pattern pattern = Pattern.compile("[^\\d\\w]");

    @Override
    public UpdateRestorePasswordDTO validate(UpdateRestorePasswordDTO value) {

        Matcher newPasswordMatcher = pattern.matcher(value.getNewPassword());

        if (newPasswordMatcher.find()) {
            throw new InvalidCharactersException(ExceptionMessage.INVALID_CHARACTERS_EXCEPTION);
        }

        if (getNext() != null) {
            return getNext().validate(value);
        }

        return value;
    }

}
