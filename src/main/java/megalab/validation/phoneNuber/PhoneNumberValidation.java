package megalab.validation.phoneNuber;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PhoneNumberValidation {
    String message() default "Use country code '+996' and length phone number must be 13 symbol";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
