package com.pet.stock_price_tracker.controller.user;

import com.pet.stock_price_tracker.constants.Routes;
import com.pet.stock_price_tracker.dto.user.UserDTO;
import com.pet.stock_price_tracker.dto.user.UserResponseDTO;
import com.pet.stock_price_tracker.service.user.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(Routes.API_USER_ROUTE)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> registration(@RequestBody @Validated UserDTO userDTO) {

        UserResponseDTO user = userService.createUser(userDTO);

        return ResponseEntity.created(URI.create(Routes.LOGIN_ROUTE)).body(user);
    }
}
