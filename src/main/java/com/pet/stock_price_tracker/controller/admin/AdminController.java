package com.pet.stock_price_tracker.controller.admin;

import com.pet.stock_price_tracker.constants.Message;
import com.pet.stock_price_tracker.constants.Routes;
import com.pet.stock_price_tracker.dto.admin.AdminCreateUserDTO;
import com.pet.stock_price_tracker.dto.ticker.MessageDTO;
import com.pet.stock_price_tracker.service.admin.AdminService;
import org.springframework.http.ResponseEntity;
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

    @PostMapping(Routes.ADMIN_CREATE_USER_ROUTE)
    public ResponseEntity<MessageDTO> createUser(@RequestBody @Validated AdminCreateUserDTO adminCreateUserDTO) {

        adminService.createUser(adminCreateUserDTO);

        return ResponseEntity.ok(new MessageDTO(Message.SUCCESS_MESSAGE));
    }
}
