package megalab.validation.notNull;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.hibernate.validator.internal.constraintvalidators.bv.NotNullValidator;

import java.lang.annotation.*;

@Constraint(validatedBy = NotNullValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NotNullValid {
    String message() default "Must not be blank or empty!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
