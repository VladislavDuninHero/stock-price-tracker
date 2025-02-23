package com.pet.stock_price_tracker.service.validation.validators.user.registration;

import com.pet.stock_price_tracker.constants.ExceptionMessage;
import com.pet.stock_price_tracker.dto.user.registration.UserDTO;
import com.pet.stock_price_tracker.exception.UserIsAlreadyRegisteredException;
import com.pet.stock_price_tracker.repository.UserRepository;
import com.pet.stock_price_tracker.service.validation.validators.BaseValidator;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(4)
@Component
public class UserEmailIsAlreadyRegisteredValidator extends BaseValidator<UserDTO> {

    private final UserRepository userRepository;

    public UserEmailIsAlreadyRegisteredValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO validate(UserDTO value) {
        if (userRepository.findUserByEmail(value.getEmail()).isPresent()) {
            throw new UserIsAlreadyRegisteredException(ExceptionMessage.USER_IS_ALREADY_REGISTERED_EXCEPTION);
        }

        if (getNext() != null) {
            return getNext().validate(value);
        }

        return value;
    }
}
