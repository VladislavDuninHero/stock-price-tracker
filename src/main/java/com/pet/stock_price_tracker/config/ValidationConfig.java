package com.pet.stock_price_tracker.config;

import com.pet.stock_price_tracker.dto.user.UserDTO;
import com.pet.stock_price_tracker.service.validation.validators.BaseValidator;
import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Configuration
public class ValidationConfig {

    private final List<BaseValidator<UserDTO>> userValidators;

    public ValidationConfig(List<BaseValidator<UserDTO>> userValidators) {
        this.userValidators = userValidators;
    }

    @Bean
    public List<BaseValidator<UserDTO>> configurateUserValidators() {
        for (int i = 1; i < userValidators.size(); i++) {
            userValidators.get(i - 1).setNext(userValidators.get(i));
        }

        return userValidators;
    }
}
