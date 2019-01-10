package lv.helloit.lottery.entities.participants.entities;

import lv.helloit.lottery.entities.lotteries.constraints.IsGreaterThanZero;

public class IdValidationWrapper {
    @IsGreaterThanZero
    private String id;

    public IdValidationWrapper() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id.trim();
    }
}
