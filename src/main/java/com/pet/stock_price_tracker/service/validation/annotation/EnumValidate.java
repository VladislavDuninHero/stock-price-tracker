package com.pet.stock_price_tracker.service.validation.annotation;

import com.pet.stock_price_tracker.service.validation.annotation.validator.EnumValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EnumValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumValidate {
    Class<? extends Enum<?>> enumClass();
    String message();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
