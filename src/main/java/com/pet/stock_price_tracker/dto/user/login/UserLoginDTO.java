package com.pet.stock_price_tracker.dto.user.login;

import com.pet.stock_price_tracker.constants.ExceptionMessage;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {

    @NotNull(message = ExceptionMessage.LOGIN_IS_REQUIRED)
    @NotEmpty(message = ExceptionMessage.LOGIN_IS_NOT_BE_EMPTY)
    @Length(min = 1, max = 64, message = ExceptionMessage.LOGIN_LENGTH_IS_INVALID)
    private String login;

    @NotNull(message = ExceptionMessage.PASSWORD_IS_REQUIRED)
    @NotEmpty(message = ExceptionMessage.PASSWORD_IS_NOT_BE_EMPTY)
    @Length(min = 6, max = 64, message = ExceptionMessage.PASSWORD_LENGTH_IS_INVALID)
    private String password;
}
