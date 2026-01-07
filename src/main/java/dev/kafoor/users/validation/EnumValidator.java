package dev.kafoor.users.validation;

import dev.kafoor.users.annotation.ValidEnumValue;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<ValidEnumValue, Enum<?>> {
    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        return value != null;
    }
}