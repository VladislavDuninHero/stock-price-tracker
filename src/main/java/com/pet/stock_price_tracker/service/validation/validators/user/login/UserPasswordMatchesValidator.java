package com.pet.stock_price_tracker.service.validation.validators.user.login;

import com.pet.stock_price_tracker.constants.ExceptionMessage;
import com.pet.stock_price_tracker.dto.user.login.UserLoginDTO;
import com.pet.stock_price_tracker.entity.User;
import com.pet.stock_price_tracker.exception.UserNotFoundException;
import com.pet.stock_price_tracker.exception.UserPasswordIsInvalid;
import com.pet.stock_price_tracker.repository.UserRepository;
import com.pet.stock_price_tracker.service.validation.validators.BaseValidator;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Order(3)
@Component
public class UserPasswordMatchesValidator extends BaseValidator<UserLoginDTO> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserPasswordMatchesValidator(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserLoginDTO validate(UserLoginDTO value) {
        User user = userRepository.findUserByLogin(value.getLogin())
                .orElseThrow(() -> new UserNotFoundException(ExceptionMessage.USER_NOT_FOUND_EXCEPTION));

        if (!passwordEncoder.matches(value.getPassword(), user.getPassword())) {
            throw new UserPasswordIsInvalid(ExceptionMessage.USER_PASSWORD_IS_INVALID);
        }

        if (getNext() != null) {
            return getNext().validate(value);
        }

        return value;
    }

}
