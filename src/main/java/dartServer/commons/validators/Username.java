package dartServer.commons.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The annotated element must be of the user name type.
 */
@NotNull
@Size(min = 3, max = 20)
@Pattern(regexp = "[a-zA-Z0-9]*")

@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface Username {

    String message() default "is not a valid name for a user";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
