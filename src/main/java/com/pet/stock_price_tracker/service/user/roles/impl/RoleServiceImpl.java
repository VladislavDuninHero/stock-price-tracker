package com.pet.stock_price_tracker.service.user.roles.impl;

import com.pet.stock_price_tracker.constants.ExceptionMessage;
import com.pet.stock_price_tracker.entity.Permission;
import com.pet.stock_price_tracker.entity.Role;
import com.pet.stock_price_tracker.entity.User;
import com.pet.stock_price_tracker.enums.Roles;
import com.pet.stock_price_tracker.exception.UserNotFoundException;
import com.pet.stock_price_tracker.repository.UserRepository;
import com.pet.stock_price_tracker.service.user.roles.RoleService;
import com.pet.stock_price_tracker.service.user.roles.factory.RolePermissionsFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RolePermissionsFactory rolePermissionsFactory;
    private final UserRepository userRepository;

    public RoleServiceImpl(
            RolePermissionsFactory rolePermissionsFactory,
            UserRepository userRepository
    ) {
        this.rolePermissionsFactory = rolePermissionsFactory;
        this.userRepository = userRepository;
    }

    @Override
    public void createRoleForUser(User user, Roles role) {
        Role roleEntity = new Role();
        roleEntity.setRole(role);

        List<Permission> permissions = rolePermissionsFactory.getRolePermissions(role)
                .stream()
                .map(Permission::new)
                .toList();

        roleEntity.setPermissions(permissions);

        if (user.getRoles() == null) {
            user.setRoles(new ArrayList<>());
        }

        user.getRoles().add(roleEntity);
    }

    @Override
    public List<Role> getRoleForUser(String login) {
        User user = userRepository.findUserByLogin(login)
                .orElseThrow(() -> new UserNotFoundException(ExceptionMessage.USER_NOT_FOUND_EXCEPTION));

        return user.getRoles();
    }
}
