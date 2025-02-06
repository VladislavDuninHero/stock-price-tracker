package com.pet.stock_price_tracker.dto.user.registration;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotNull
    @NotEmpty
    @Length(min = 1, max = 64)
    private String login;

    @NotNull
    @NotEmpty
    @Length(min = 6, max = 64)
    private String password;

    @NotNull
    @NotEmpty
    @Email
    private String email;
}
