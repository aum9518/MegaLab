package megalab.validation.nickName;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import megalab.validation.email.EmailValidator;

import java.lang.annotation.*;

@Constraint(validatedBy = NickNameValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NickNameValid {
    String message() default "This nick name already has been taken...";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
