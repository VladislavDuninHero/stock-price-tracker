package com.pet.stock_price_tracker.dto.admin;

import com.pet.stock_price_tracker.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminCreateUserDTO {
    private String login;
    private String password;
    private String email;
    private Roles role;
}
