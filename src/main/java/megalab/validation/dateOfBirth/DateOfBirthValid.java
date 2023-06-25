package megalab.validation.dateOfBirth;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import megalab.validation.password.PasswordValidator;

import java.lang.annotation.*;

@Constraint(validatedBy = DateOfBirthValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DateOfBirthValid {
    String message() default "You are not born yet...";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
