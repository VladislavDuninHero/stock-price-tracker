package com.pet.stock_price_tracker.service.user.roles.factory;

import com.pet.stock_price_tracker.enums.PermissionType;
import com.pet.stock_price_tracker.enums.Roles;

import java.util.List;

public interface RolePermissionsFactory {
    List<PermissionType> getRolePermissions(Roles role);
}
