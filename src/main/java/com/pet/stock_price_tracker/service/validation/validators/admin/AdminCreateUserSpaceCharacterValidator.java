package com.pet.stock_price_tracker.service.validation.validators.admin;

import com.pet.stock_price_tracker.constants.ExceptionMessage;
import com.pet.stock_price_tracker.dto.admin.AdminCreateUserDTO;
import com.pet.stock_price_tracker.dto.user.registration.UserDTO;
import com.pet.stock_price_tracker.exception.SpaceSymbolException;
import com.pet.stock_price_tracker.service.validation.validators.BaseValidator;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(2)
@Component
public class AdminCreateUserSpaceCharacterValidator extends BaseValidator<AdminCreateUserDTO> {
    @Override
    public AdminCreateUserDTO validate(AdminCreateUserDTO value) {
        if (value.getLogin().contains(" ") || value.getPassword().contains(" ")) {
            throw new SpaceSymbolException(ExceptionMessage.SPACES_SYMBOLS_CONTAINS);
        }

        if (getNext() != null) {
            return getNext().validate(value);
        }

        return value;
    }
}
