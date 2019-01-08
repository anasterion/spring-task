package lv.helloit.lottery.participants.constraints;

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

    String message() default "value can't be empty and person must be older than 21 to participate";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
