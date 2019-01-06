package lv.helloit.lottery.lotteries.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = IsGreaterThanZeroConstraintValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD } )
@Retention(RetentionPolicy.RUNTIME)
public @interface IsGreaterThanZero {

    String message() default "value can't be empty and should be a number greater than zero";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
