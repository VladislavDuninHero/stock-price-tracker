package com.pet.stock_price_tracker.controller.admin;

import com.pet.stock_price_tracker.constants.Message;
import com.pet.stock_price_tracker.constants.Routes;
import com.pet.stock_price_tracker.dto.admin.AdminCreateUserDTO;
import com.pet.stock_price_tracker.dto.admin.AdminLoginDTO;
import com.pet.stock_price_tracker.dto.ticker.MessageDTO;
import com.pet.stock_price_tracker.dto.user.login.UserResponseLoginDTO;
import com.pet.stock_price_tracker.service.admin.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Routes.ADMIN_ROUTE)
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping(Routes.LOGIN_ROUTE)

    public ResponseEntity<UserResponseLoginDTO> login(@RequestBody @Validated AdminLoginDTO loginDTO) {

        UserResponseLoginDTO loginResponse = adminService.login(loginDTO);

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping(Routes.ADMIN_CREATE_USER_ROUTE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageDTO> createUser(@RequestBody @Validated AdminCreateUserDTO adminCreateUserDTO) {

        adminService.createUser(adminCreateUserDTO);

        return ResponseEntity.ok(new MessageDTO(Message.SUCCESS_MESSAGE));
    }
}
