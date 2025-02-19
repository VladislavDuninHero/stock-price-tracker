package com.pet.stock_price_tracker.controller.admin;

import com.pet.stock_price_tracker.constants.Routes;
import com.pet.stock_price_tracker.dto.admin.AdminCreateUserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Routes.ADMIN_ROUTE)
public class AdminController {

    @PostMapping(Routes.ADMIN_CREATE_USER_ROUTE)
    public ResponseEntity<String> createUser(AdminCreateUserDTO adminCreateUserDTO) {



        return ResponseEntity.ok("");
    }
}
