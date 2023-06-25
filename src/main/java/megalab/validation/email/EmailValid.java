package megalab.validation.email;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import megalab.validation.dateOfBirth.DateOfBirthValidator;

import java.lang.annotation.*;

@Constraint(validatedBy = EmailValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EmailValid {
    String message() default "This email already exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
