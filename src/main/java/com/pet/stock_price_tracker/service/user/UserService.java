package com.pet.stock_price_tracker.service.user;

import com.pet.stock_price_tracker.dto.user.UserDTO;
import com.pet.stock_price_tracker.dto.user.UserResponseDTO;

public interface UserService {
    UserResponseDTO createUser(UserDTO userDTO);
    UserResponseDTO findUserByLogin(String login);
}
