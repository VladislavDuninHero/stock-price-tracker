package com.pet.stock_price_tracker.dto.user;

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
public class UserDTO {

    @Length(min = 1, max = 64)
    @NotNull
    @NotEmpty
    private String login;

    @Length(min = 6, max = 64)
    @NotNull
    @NotEmpty
    private String password;
}
