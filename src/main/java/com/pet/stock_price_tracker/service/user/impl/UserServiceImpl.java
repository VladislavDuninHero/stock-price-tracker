package com.pet.stock_price_tracker.service.user.impl;

import com.pet.stock_price_tracker.dto.user.UserDTO;
import com.pet.stock_price_tracker.dto.user.UserResponseDTO;
import com.pet.stock_price_tracker.entity.User;
import com.pet.stock_price_tracker.repository.UserRepository;
import com.pet.stock_price_tracker.service.user.UserService;
import com.pet.stock_price_tracker.service.utils.mapping.UserMapper;
import com.pet.stock_price_tracker.service.validation.manager.impl.UserValidationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserValidationManager validationManager;

    public UserServiceImpl(
            UserRepository userRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder,
            UserValidationManager validationManager
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.validationManager = validationManager;
    }

    @Override
    public UserResponseDTO createUser(UserDTO userDTO) {

        validationManager.validate(userDTO);

        User user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);

        return userMapper.toDTO(savedUser);
    }

    @Override
    public UserResponseDTO findUserByLogin(String login) {
        return null;
    }
}
