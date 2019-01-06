package lv.helloit.lottery.participants.constraints;

import lv.helloit.lottery.lotteries.constraints.IsGreaterThanZeroConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AgeReqConstraintValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD } )
@Retention(RetentionPolicy.RUNTIME)
public @interface AgeReq {

    String message() default "value should be number greater than 21";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
