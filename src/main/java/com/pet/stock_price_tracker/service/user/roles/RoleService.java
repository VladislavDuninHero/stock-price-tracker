package com.pet.stock_price_tracker.service.user.roles;

import com.pet.stock_price_tracker.entity.Role;
import com.pet.stock_price_tracker.entity.User;
import com.pet.stock_price_tracker.enums.Roles;

import java.util.List;

public interface RoleService {
    void createRoleForUser(User user, Roles role);
    List<Role> getRoleForUser(String login);
}
