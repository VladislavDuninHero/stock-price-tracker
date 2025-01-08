package com.pet.stock_price_tracker.service.utils.mapping;

import com.pet.stock_price_tracker.dto.user.UserDTO;
import com.pet.stock_price_tracker.dto.user.UserResponseDTO;
import com.pet.stock_price_tracker.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "login", target = "login")
    @Mapping(source = "password", target = "password")
    User toEntity(UserDTO userDTO);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "login", target = "login")
    UserResponseDTO toDTO(User user);
}
