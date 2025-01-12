package com.pet.stock_price_tracker.service.user;

import com.pet.stock_price_tracker.dto.security.JwtDTO;
import com.pet.stock_price_tracker.dto.user.login.UserLoginDTO;
import com.pet.stock_price_tracker.dto.user.login.UserResponseLoginDTO;
import com.pet.stock_price_tracker.dto.user.registration.UserDTO;
import com.pet.stock_price_tracker.dto.user.registration.UserResponseDTO;

import java.util.List;

public interface UserService {
    UserResponseDTO createUser(UserDTO userDTO);
    UserResponseLoginDTO login(UserLoginDTO userLoginDTO);
    UserResponseDTO findUserByLogin(String login);
    JwtDTO refreshToken(String refreshToken);
    List<UserResponseDTO> getAllUsers();
}
