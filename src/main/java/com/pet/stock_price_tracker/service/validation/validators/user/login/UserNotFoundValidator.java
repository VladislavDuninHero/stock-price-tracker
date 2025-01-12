package com.pet.stock_price_tracker.service.validation.validators.user.login;

import com.pet.stock_price_tracker.constants.ExceptionMessage;
import com.pet.stock_price_tracker.dto.user.login.UserLoginDTO;
import com.pet.stock_price_tracker.exception.UserNotFoundException;
import com.pet.stock_price_tracker.repository.UserRepository;
import com.pet.stock_price_tracker.service.validation.validators.BaseValidator;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(2)
@Component
public class UserNotFoundValidator extends BaseValidator<UserLoginDTO> {

    private final UserRepository userRepository;

    public UserNotFoundValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserLoginDTO validate(UserLoginDTO value) {
        userRepository.findUserByLogin(value.getLogin())
                .orElseThrow(() -> new UserNotFoundException(ExceptionMessage.USER_NOT_FOUND_EXCEPTION));

        if (getNext() != null) {
            return getNext().validate(value);
        }

        return value;
    }

}
