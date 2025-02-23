package com.pet.stock_price_tracker.service.user.roles.factory.impl;

import com.pet.stock_price_tracker.enums.PermissionType;
import com.pet.stock_price_tracker.enums.Roles;
import com.pet.stock_price_tracker.service.user.roles.factory.RolePermissionsFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Scope("prototype")
public class RolePermissionsFactoryImpl implements RolePermissionsFactory {

    private final Map<Roles, List<PermissionType>> rolePermissions = Map.ofEntries(
            Map.entry(Roles.USER, List.of(
                    PermissionType.GET_TICKERS_PERMISSION,
                    PermissionType.SAVE_TICKERS_PERMISSION,
                    PermissionType.DELETE_USER_PERMISSION,
                    PermissionType.GET_USER_PROFILE_PERMISSION
            )),
            Map.entry(Roles.ADMIN, List.of(
                    PermissionType.GET_TICKERS_PERMISSION,
                    PermissionType.SAVE_TICKERS_PERMISSION,
                    PermissionType.CREATE_USER_PERMISSION,
                    PermissionType.GET_USER_PERMISSION,
                    PermissionType.GET_USERS_PERMISSION,
                    PermissionType.DELETE_USER_PERMISSION
            ))
    );

    @Override
    public List<PermissionType> getRolePermissions(Roles role) {
        if (!rolePermissions.containsKey(role)) {
            throw new IllegalArgumentException("Invalid role");
        }

        return rolePermissions.get(role);
    }
}
