package com.pet.stock_price_tracker.service.validation.validators.admin;

import com.pet.stock_price_tracker.constants.ExceptionMessage;
import com.pet.stock_price_tracker.dto.admin.AdminCreateUserDTO;
import com.pet.stock_price_tracker.exception.UserIsAlreadyRegisteredException;
import com.pet.stock_price_tracker.repository.UserRepository;
import com.pet.stock_price_tracker.service.validation.validators.BaseValidator;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(3)
@Component
public class AdminCreateUserUserAlreadyRegisteredValidator extends BaseValidator<AdminCreateUserDTO> {

    private final UserRepository userRepository;

    public AdminCreateUserUserAlreadyRegisteredValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public AdminCreateUserDTO validate(AdminCreateUserDTO value) {
        if (userRepository.findUserByLogin(value.getLogin()).isPresent()) {
            throw new UserIsAlreadyRegisteredException(ExceptionMessage.USER_IS_ALREADY_REGISTERED_EXCEPTION);
        }

        if (getNext() != null) {
            return getNext().validate(value);
        }

        return value;
    }
}