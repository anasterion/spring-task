package lv.helloit.lottery.participants.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AgeReqConstraintValidator implements ConstraintValidator<AgeReq, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        try {
            int tmpInt = Integer.parseInt(value);

            if (tmpInt > 20 && tmpInt < 66) {
                return true;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        return false;
    }
}
