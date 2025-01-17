package com.pet.stock_price_tracker.service.validation.annotation.validator;

import com.pet.stock_price_tracker.service.validation.annotation.EnumValidate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<EnumValidate, String> {

    private Enum<?>[] enums;

    @Override
    public void initialize(EnumValidate constraintAnnotation) {
        enums = constraintAnnotation.enumClass().getEnumConstants();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        for (Enum<?> enumConstant : enums) {
            if (enumConstant.name().equalsIgnoreCase(s)) {
                return true;
            }
        }

        return false;
    }

}
