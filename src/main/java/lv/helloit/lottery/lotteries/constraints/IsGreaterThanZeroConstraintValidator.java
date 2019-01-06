package lv.helloit.lottery.lotteries.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsGreaterThanZeroConstraintValidator implements ConstraintValidator<IsGreaterThanZero, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        try {
            int tmpInt = Integer.parseInt(value);

            if (tmpInt > 0) {
                return true;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        return false;
    }
}
