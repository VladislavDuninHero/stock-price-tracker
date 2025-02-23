package com.pet.stock_price_tracker.service.utils.mapping;

import com.pet.stock_price_tracker.dto.admin.AdminCreateUserDTO;
import com.pet.stock_price_tracker.dto.user.registration.UserDTO;
import com.pet.stock_price_tracker.dto.user.registration.UserResponseDTO;
import com.pet.stock_price_tracker.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "login", target = "login")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "email", target = "email")
    User toEntity(UserDTO userDTO);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "login", target = "login")
    UserResponseDTO toDTO(User user);

    @Mapping(source = "login", target = "login")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "email", target = "email")
    User toEntity(AdminCreateUserDTO adminCreateUserDTO);
}
