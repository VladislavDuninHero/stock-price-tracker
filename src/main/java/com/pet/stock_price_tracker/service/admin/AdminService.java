package com.pet.stock_price_tracker.service.admin;

import com.pet.stock_price_tracker.dto.admin.AdminCreateUserDTO;
import com.pet.stock_price_tracker.dto.admin.AdminLoginDTO;
import com.pet.stock_price_tracker.dto.user.login.UserResponseLoginDTO;

public interface AdminService {
    UserResponseLoginDTO login(AdminLoginDTO adminLoginDTO);
    void createUser(AdminCreateUserDTO adminCreateUserDTO);
}
