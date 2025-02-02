package com.pet.stock_price_tracker.controller.user;

import com.pet.stock_price_tracker.constants.Routes;
import com.pet.stock_price_tracker.dto.security.JwtDTO;
import com.pet.stock_price_tracker.dto.security.RefreshAccessTokenDTO;
import com.pet.stock_price_tracker.dto.user.login.UserLoginDTO;
import com.pet.stock_price_tracker.dto.user.login.UserResponseLoginDTO;
import com.pet.stock_price_tracker.dto.user.registration.UserDTO;
import com.pet.stock_price_tracker.dto.user.registration.UserResponseDTO;
import com.pet.stock_price_tracker.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(Routes.API_USER_ROUTE)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(Routes.REGISTRATION_ROUTE)
    public ResponseEntity<UserResponseDTO> registration(@RequestBody @Validated UserDTO userDTO) {

        UserResponseDTO user = userService.createUser(userDTO);;

        return ResponseEntity.created(URI.create(Routes.LOGIN_ROUTE)).body(user);
    }

    @PostMapping(Routes.LOGIN_ROUTE)
    public ResponseEntity<UserResponseLoginDTO> login(@RequestBody @Validated UserLoginDTO userLoginDTO) {

        UserResponseLoginDTO user = userService.login(userLoginDTO);

        return ResponseEntity.ok().body(user);
    }

    @PostMapping(Routes.REFRESH_TOKEN_ROUTE)
    public ResponseEntity<JwtDTO> refresh(@RequestBody RefreshAccessTokenDTO refreshAccessTokenDTO) {
        JwtDTO accessToken = userService.refreshToken(refreshAccessTokenDTO.getRefreshToken());

        return ResponseEntity.ok(accessToken);
    }

    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }
}
