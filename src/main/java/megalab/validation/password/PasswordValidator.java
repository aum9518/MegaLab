package megalab.validation.password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordValid,String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return password.length()>4;
    }
}
