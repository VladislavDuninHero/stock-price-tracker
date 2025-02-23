package com.pet.stock_price_tracker.service.validation.validators.admin;

import com.pet.stock_price_tracker.constants.ExceptionMessage;
import com.pet.stock_price_tracker.dto.admin.AdminCreateUserDTO;
import com.pet.stock_price_tracker.enums.Roles;
import com.pet.stock_price_tracker.exception.RoleValidationException;
import com.pet.stock_price_tracker.service.validation.validators.BaseValidator;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Order(4)
@Component
public class AdminCreateUserRoleValidator extends BaseValidator<AdminCreateUserDTO> {

    @Override
    public AdminCreateUserDTO validate(AdminCreateUserDTO value) {
        Roles[] roles = Roles.values();
        List<Roles> userRequestRoles = value.getRoles();

        boolean isExists = false;
        for (Roles userRole : userRequestRoles) {
            isExists = false;

            for (Roles role : roles) {
                if (userRole.name().equals(role.name())) {
                    isExists = true;
                    break;
                }
            }

            if (!isExists) {
                break;
            }
        }

        if (!isExists) {
            throw new RoleValidationException(ExceptionMessage.ROLE_IS_INVALID_EXCEPTION);
        }

        if (getNext() != null) {
            return getNext().validate(value);
        }

        return value;
    }
}
