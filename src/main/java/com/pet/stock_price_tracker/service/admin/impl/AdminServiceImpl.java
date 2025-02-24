package com.pet.stock_price_tracker.service.admin.impl;

import com.pet.stock_price_tracker.dto.admin.AdminCreateUserDTO;
import com.pet.stock_price_tracker.dto.admin.AdminLoginDTO;
import com.pet.stock_price_tracker.dto.user.login.UserResponseLoginDTO;
import com.pet.stock_price_tracker.entity.User;
import com.pet.stock_price_tracker.enums.Roles;
import com.pet.stock_price_tracker.repository.UserRepository;
import com.pet.stock_price_tracker.service.admin.AdminService;
import com.pet.stock_price_tracker.service.user.roles.RoleService;
import com.pet.stock_price_tracker.service.utils.mapping.UserMapper;
import com.pet.stock_price_tracker.service.validation.manager.impl.AdminUserRegistrationRequestValidationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final AdminUserRegistrationRequestValidationManager adminUserRegistrationRequestValidationManager;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public AdminServiceImpl(
            UserRepository userService,
            AdminUserRegistrationRequestValidationManager adminUserRegistrationRequestValidationManager,
            PasswordEncoder passwordEncoder,
            RoleService roleService
    ) {
        this.userRepository = userService;
        this.adminUserRegistrationRequestValidationManager = adminUserRegistrationRequestValidationManager;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public UserResponseLoginDTO login(AdminLoginDTO adminLoginDTO) {



        return null;
    }

    @Override
    public void createUser(AdminCreateUserDTO adminCreateUserDTO) {
        adminUserRegistrationRequestValidationManager.validate(adminCreateUserDTO);

        User user = User.builder()
                .login(adminCreateUserDTO.getLogin())
                .password(passwordEncoder.encode(adminCreateUserDTO.getPassword()))
                .email(adminCreateUserDTO.getEmail())
                .roles(new ArrayList<>())
                .build();

        for (Roles role : adminCreateUserDTO.getRoles()) {
            roleService.createRoleForUser(user, role);
        }

        userRepository.save(user);
    }
}
