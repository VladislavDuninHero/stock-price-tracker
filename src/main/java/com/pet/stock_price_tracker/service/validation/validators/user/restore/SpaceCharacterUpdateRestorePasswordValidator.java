package com.pet.stock_price_tracker.service.validation.validators.user.restore;

import com.pet.stock_price_tracker.constants.ExceptionMessage;
import com.pet.stock_price_tracker.dto.user.registration.UserDTO;
import com.pet.stock_price_tracker.dto.user.restore.UpdateRestorePasswordDTO;
import com.pet.stock_price_tracker.exception.SpaceSymbolException;
import com.pet.stock_price_tracker.service.validation.validators.BaseValidator;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(2)
@Component
public class SpaceCharacterUpdateRestorePasswordValidator extends BaseValidator<UpdateRestorePasswordDTO> {
    @Override
    public UpdateRestorePasswordDTO validate(UpdateRestorePasswordDTO value) {
        if (value.getNewPassword().contains(" ")) {
            throw new SpaceSymbolException(ExceptionMessage.SPACES_SYMBOLS_CONTAINS);
        }

        if (getNext() != null) {
            return getNext().validate(value);
        }

        return value;
    }
}
