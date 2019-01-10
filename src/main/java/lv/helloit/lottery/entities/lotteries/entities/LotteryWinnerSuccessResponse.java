package lv.helloit.lottery.entities.lotteries.entities;

import java.util.Objects;

public class LotteryWinnerSuccessResponse extends LotteryResponse {
    private String status;
    private String winnerCode;

    public LotteryWinnerSuccessResponse(String status, String winnerCode) {
        this.status = status;
        this.winnerCode = winnerCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWinnerCode() {
        return winnerCode;
    }

    public void setWinnerCode(String winnerCode) {
        this.winnerCode = winnerCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LotteryWinnerSuccessResponse that = (LotteryWinnerSuccessResponse) o;
        return Objects.equals(status, that.status) &&
                Objects.equals(winnerCode, that.winnerCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, winnerCode);
    }

    @Override
    public String toString() {
        return "LotteryWinnerSuccessResponse{" +
                "status='" + status + '\'' +
                ", winnerCode='" + winnerCode + '\'' +
                '}';
    }
}
