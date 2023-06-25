package megalab.validation.notNull;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NutNullValidation implements ConstraintValidator<NotNullValid,String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        return value.isBlank() ||  value.isEmpty();
    }
}
