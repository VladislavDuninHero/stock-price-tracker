package com.pet.stock_price_tracker.dto.user.login;

import com.pet.stock_price_tracker.dto.security.JwtDTO;
import com.pet.stock_price_tracker.dto.user.registration.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseLoginDTO {

    private UserResponseDTO userInfo;

    private JwtDTO authentication;
}
